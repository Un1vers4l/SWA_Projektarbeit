/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 14:14:30
 * @modify date 2022-01-22 14:14:30
 * @desc [description]
 */

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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.logging.Logger;
import de.hsos.swa.studiom.StudentsManagement.boundary.dto.StudentDTO;
import de.hsos.swa.studiom.StudentsManagement.boundary.dto.newStudentDTO;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudentsManagement.gateway.StudentRepository;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api/v1/student/{matNr}")
@ApplicationScoped
public class StudentMatNrRessource {
    
    Logger log = Logger.getLogger(StudentMatNrRessource.class);

    @Context
    UriInfo uriInfo;
    
    // Named und interface inj
    @Inject
    StudentRepository service;

    @GET
    @Operation(summary = "find a Student", description = "Find a student with their matNr")
    public Response getStudent(@PathParam("matNr") int matNr) {
        log.info("GET " + uriInfo.getPath());
        Optional<Student> opt = service.getStudent(matNr);
        if (opt.isPresent()) {
            return Response.ok(StudentDTO.Converter.toStudentDTO(opt.get())).build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @POST
    @Operation(summary = "Change a student", description = "Change the E-Mail and name of a student")
    public Response changeStudent(@PathParam("matNr") int matNr, newStudentDTO newStudent) {
        log.info("POST " + uriInfo.getPath());
        Optional<Student> opt = service.changeStudent(matNr, newStudentDTO.Converter.toStudent(newStudent));
        if (opt.isPresent()) {
            return Response.ok(StudentDTO.Converter.toStudentDTO(opt.get())).build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @PUT

    public Response notImplementedResponse() {
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }

    @DELETE
    @Operation(summary = "Delete a student")
    public Response deleteStudent(@PathParam("matNr") int matNr) {
        log.info("DELETE " + uriInfo.getPath());
        boolean deleted = service.deleteStudent(matNr);
        if (deleted) {
            return Response.ok().build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

}
