/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-02-06 08:39:58
 * @modify date 2022-02-06 08:39:58
 * @desc [description]
 */
package de.hsos.swa.studiom.UserManagement.boundary.http;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.jboss.resteasy.annotations.Form;

import de.hsos.swa.studiom.StudentsManagement.boundary.dto.HTTPStudentDTO;
import de.hsos.swa.studiom.StudentsManagement.control.StudentService;
import de.hsos.swa.studiom.StudyGroupManagement.control.ProjectService;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;
import de.hsos.swa.studiom.shared.exceptions.WrongUserDataExeption;
import io.quarkus.qute.Template;
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
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.logging.Logger;

import de.hsos.swa.studiom.UserManagement.boundary.dto.AuthDto;
import de.hsos.swa.studiom.UserManagement.boundary.dto.TokenDto;
import de.hsos.swa.studiom.UserManagement.control.AuthService;
import de.hsos.swa.studiom.shared.dto.StatusDto;

@Transactional
@Produces(MediaType.TEXT_HTML)
@Consumes(MediaType.TEXT_HTML)
@Path("/login")
@PermitAll
public class LoginRessource {
    @Inject
    Template login;

    @Inject
    AuthService authService;

    @Inject
    Validator validator;

    @Context
    UriInfo uriInfo;

    @GET
    public Response loginPage() {
        return Response.ok(login.render()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("user") String user, @FormParam("password") String password) {
        AuthDto authDto = new AuthDto(user, password);
        System.out.println(user + "  " + password);
        Set<ConstraintViolation<AuthDto>> violations = validator.validate(authDto);
        if (!violations.isEmpty()) {
            return Response.ok(new StatusDto(violations)).build();
        }

        Optional<String> token;
        try {
            token = authService.userLogin(authDto.getUsername(), authDto.getPassword());
        } catch (WrongUserDataExeption e) {
            return Response.ok(new StatusDto(e)).build();
        }

        if (!token.isPresent()) {
            return Response.ok(
                    new StatusDto(
                            "es ist ein fehler aufgetreten bitte kontaktieren Sie denn Support oder versuchen Sie es nochmal"))
                    .build();
        }
        System.out.println(token.get());
        return Response.ok(token.get()).build();
    }
}
