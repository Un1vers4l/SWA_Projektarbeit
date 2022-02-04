/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-01-31 15:10:49
 * @modify date 2022-01-31 15:10:49
 * @desc [description]
 */
package de.hsos.swa.studiom.UserManagement.boundary.rest;


import java.util.Optional;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.validation.ConstraintViolation;

import de.hsos.swa.studiom.UserManagement.boundary.dto.ChangePasswordDto;
import de.hsos.swa.studiom.UserManagement.boundary.dto.UserDto;
import de.hsos.swa.studiom.UserManagement.control.UserService;
import de.hsos.swa.studiom.UserManagement.entity.Role;
import de.hsos.swa.studiom.UserManagement.entity.User;
import de.hsos.swa.studiom.shared.dto.StatusDto;
import de.hsos.swa.studiom.shared.exceptions.UserNotExistExeption;

@RequestScoped
@Path("api/v1/User/{userid}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserIDRest {

    @Inject
    Validator validator;

    @Inject
    UserService userService;

    @Context SecurityContext ctx;

    @RolesAllowed("ADMIN")
    @GET
    @Operation(summary = "Findet einen User ueber die ID Rechte: {ADMIN}", description = "Gibt den User zurueck")
    public Response getUser(@PathParam("userid") long userID){

        Optional<User> user = userService.findUser(userID);
        
        if(!user.isPresent()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(UserDto.Converter.UserToDto(user.get())).build();
    }


    @RolesAllowed("USER")
    @PUT
    @Operation(summary = "Aendert das Password vom User Rechte: {ALLE}", description = "Als Admin koennen Sie von jedem das Password aendern und als User nur Ihr eigenes")
    public Response changeUserPassword(@PathParam("userid") long userID, ChangePasswordDto newPassword){
        //TODO validator fur bedingungen eines Password 
        Set<ConstraintViolation<ChangePasswordDto>> violations = validator.validate(newPassword);
        if(!violations.isEmpty()){
            return Response.ok(new StatusDto(violations)).build();
        }

        long tokenUserID = Long.valueOf(ctx.getUserPrincipal().getName());

        if(!ctx.isUserInRole(Role.ADMIN.name()) && tokenUserID != userID){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        try {
            userService.changePassword(userID, newPassword.getPassword());
        } catch (UserNotExistExeption e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new StatusDto("User wurde geaendert", true)).build();
    }

    @RolesAllowed("ADMIN")
    @DELETE
    @Operation(summary = "Loescht ein User Rechte: {ADMIN}")
    public Response deleteUser(@PathParam("userid") long userID){
        if(!userService.deleteUser(userID)){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new StatusDto("User wurde geloescht", true)).build();
    }
}
