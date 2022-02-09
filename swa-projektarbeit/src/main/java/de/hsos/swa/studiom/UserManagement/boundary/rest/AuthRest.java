/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-01-21 15:36:37
 * @modify date 2022-01-21 15:36:37
 * @desc [description]
 */
package de.hsos.swa.studiom.UserManagement.boundary.rest;

import java.util.Optional;
import java.util.Set;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.logging.Logger;

import de.hsos.swa.studiom.UserManagement.boundary.dto.AuthDto;
import de.hsos.swa.studiom.UserManagement.boundary.dto.TokenDto;
import de.hsos.swa.studiom.UserManagement.control.AuthService;
import de.hsos.swa.studiom.shared.dto.StatusDto;
import de.hsos.swa.studiom.shared.exceptions.WrongUserDataExeption;

@RequestScoped
@Path("api/v1/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthRest {

    Logger log = Logger.getLogger(AuthRest.class);

    @Context
    UriInfo uriInfo;

    @Inject
    AuthService authService;
    
    @Inject
    Validator validator;

    @PermitAll
    @POST
    @Operation(summary = "Login", description = "Der erzeugte Token muss oben rechts hinzugefuegt werden")
    public Response login(AuthDto authDto){
        log.info("POST " +  uriInfo.getPath());

        Set<ConstraintViolation<AuthDto>> violations = validator.validate(authDto);
        if(!violations.isEmpty()){
            return Response.ok(new StatusDto(violations)).build();
        }

 
        Optional<String> token;
        try {
            token = authService.userLogin(authDto.getUsername(), authDto.getPassword());
        } catch (WrongUserDataExeption e) {
            return Response.ok(new StatusDto(e)).build();
        }

        if(!token.isPresent()){ 
            log.warn("Beim erzeugen vom Token ist ein fehler aufgetreten");
            return Response.ok(
                new StatusDto("es ist ein fehler aufgetreten bitte kontaktieren Sie denn Support oder versuchen Sie es nochmal")
                ).build();
        };

        log.debug("Username: "+ authDto.getUsername() + ", Token: " + token);
        return Response.ok(new TokenDto(token.get())).build();
    }
}
