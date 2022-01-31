/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-01-24 18:38:26
 * @modify date 2022-01-24 18:38:26
 * @desc [description]
 */
package de.hsos.swa.studiom.UserManagement.boundary.rest;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.jwt.JsonWebToken;

import de.hsos.swa.studiom.UserManagement.algorithm.username.SimpleUsernameAlgo;
import de.hsos.swa.studiom.UserManagement.boundary.dto.ErrorDto;
import de.hsos.swa.studiom.UserManagement.control.UserService;
import de.hsos.swa.studiom.UserManagement.entity.User;

@RequestScoped
@Path("api/v1/test")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.TEXT_PLAIN)
public class TestRest {

    @Inject
    JsonWebToken jwt;

    @Inject
    UserService userService;
    
    @GET
    @RolesAllowed("STUDENT")
    @Path("student")
    public Response getTestStudent(@Context SecurityContext ctx){
        return Response.ok("ID: " + ctx.getUserPrincipal().getName() + ", Role: " + jwt.getGroups()).build();
    }

    @GET
    @RolesAllowed("ADMIN")
    @Path("admin")
    public Response getTestAdmin(@Context SecurityContext ctx){
        return Response.ok("ID: " + ctx.getUserPrincipal().getName() + ", Role: " + jwt.getGroups()).build();
    }

    @GET
    @RolesAllowed("SEKT")
    @Path("sekt")
    public Response getTestSekt(@Context SecurityContext ctx){
        return Response.ok("ID: " + ctx.getUserPrincipal().getName() + ", Role: " + jwt.getGroups()).build();
    }

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @RolesAllowed("ADMIN")
    @Path("User/Username/Generator")
    public Response creatUser(){
        User user = null;
     
        user = userService.createUserStudent(new SimpleUsernameAlgo("Marc", "Kla"), "123");

        if(user == null) return Response.ok(new ErrorDto("Wurde nicht erzeugt")).build();

        return Response.ok(user).build();
    }
}
