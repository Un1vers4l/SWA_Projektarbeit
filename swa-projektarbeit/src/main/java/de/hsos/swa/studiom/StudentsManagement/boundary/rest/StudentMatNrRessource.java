/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 14:14:30
 * @modify date 2022-02-01 14:47:46
 * @desc [description]
 */

package de.hsos.swa.studiom.StudentsManagement.boundary.rest;

import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.logging.Logger;

import de.hsos.swa.studiom.StudentsManagement.boundary.dto.Student.StudentDTO;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudentsManagement.gateway.StudentRepository;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api/v1/student/{matNr}")
@Transactional
@RequestScoped
public class StudentMatNrRessource {

    Logger log = Logger.getLogger(StudentMatNrRessource.class);

    @Context
    UriInfo uriInfo;

    @Inject
    StudentRepository service;

    @GET
    @RolesAllowed("USER")
    @Operation(summary = "find a Student", description = "Find a student with their matNr")
    public Response getStudent(@PathParam("matNr") int matNr) {
        try {
            log.info("GET " + uriInfo.getPath());
            Optional<Student> opt = service.getStudent(matNr);
            if (opt.isPresent()) {
                return Response.ok(StudentDTO.Converter.toSimpleStudentDTO(opt.get())).build();
            }
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    public Response notImplementedResponse() {
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }

    @DELETE
    @RolesAllowed("SEKT")
    @Operation(summary = "Delete a student")
    public Response deleteStudent(@PathParam("matNr") int matNr) {
        try {
            log.info("DELETE " + uriInfo.getPath());
            boolean deleted = service.deleteStudent(matNr);
            if (deleted) {
                return Response.ok().build();
            }
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    public Response notImplemented() {
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }
}
