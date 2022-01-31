/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-01-25 18:57:16
 * @modify date 2022-01-25 18:57:16
 * @desc [description]
 */
package de.hsos.swa.studiom.UserManagement.boundary.rest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.arjuna.ats.internal.jdbc.drivers.modifiers.list;

import de.hsos.swa.studiom.UserManagement.boundary.dto.DataDto;
import de.hsos.swa.studiom.UserManagement.boundary.dto.ErrorDto;
import de.hsos.swa.studiom.UserManagement.boundary.dto.UserDto;
import de.hsos.swa.studiom.UserManagement.control.UserService;
import de.hsos.swa.studiom.UserManagement.entity.Role;
import de.hsos.swa.studiom.UserManagement.exception.UsernameExistExeption;

@RequestScoped
@Path("api/v1/User")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserRest {

    @Inject
    Validator validator;

    @Inject
    UserService userService;

    @RolesAllowed("ADMIN")
    @GET
    public Response getUser(){
        List<UserDto> list = userService.getAllUser()
        .stream()
        .map(UserDto.Converter::SimpleDto)
        .collect(Collectors.toList());
        return Response.ok(new DataDto(list)).build();
    }

    @RolesAllowed("ADMIN")
    @POST
    public Response creatUser(UserDto userDto){
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        if(!violations.isEmpty()){
            return Response.ok(new ErrorDto(violations)).build();
        }

        Set<Role> role = UserDto.Converter.roleAsEnum(userDto.getRole());
        if(role.isEmpty()){
            return Response.ok(new ErrorDto("Bitte geben Sie eine mindestens ein Role an die Existiert")).build();
        } 

        if(role.contains(Role.STUDENT)){
            return Response.ok(new ErrorDto("Student muss auf der Ressource(/Student) erstellt werden")).build();
        } 
        try {
            userService.createUser(userDto.getUsername(), userDto.getPassword(), role);
        } catch (UsernameExistExeption e) {
            return Response.ok(new ErrorDto(e)).build();
        }

        return Response.ok(new ErrorDto("Der User wurde erstellt", true)).build();
    }
}