package de.hsos.swa.studiom.StudentsManagement.boundary.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudentsManagement.gateway.StudentRepository;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/student/{matNr}")
@ApplicationScoped
public class StudentMatNrRessource {
    // Named und interface inj
    @Inject
    StudentRepository service;

    @GET
    public Response getStudent(@PathParam("matNr") int matNr) {
        Optional<Student> opt = service.getStudent(matNr);
        if (opt.isPresent()) {
            return Response.ok(opt.get()).build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @POST
    @PUT
    public Response notImplementedResponse() {
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }

    @DELETE
    public Response deleteStudent(@PathParam("matNr") int matNr) {
        boolean deleted = service.deleteStudent(matNr);
        if (deleted) {
            return Response.ok().build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

}
