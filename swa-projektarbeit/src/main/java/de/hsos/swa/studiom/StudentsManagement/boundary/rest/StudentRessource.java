/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 14:14:14
 * @modify date 2022-01-22 14:14:14
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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;

import de.hsos.swa.studiom.StudentsManagement.boundary.dto.StudentDTO;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudentsManagement.gateway.StudentRepository;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api/v1/student")
@ApplicationScoped
public class StudentRessource {

    // Named und interface inj
    @Inject
    StudentRepository service;

    @PUT
    @Operation(summary = "Create a new student", description = "Create a new student with their name")
    public Response createStudent(String name) {
        Optional<Student> opt = service.createStudent(name);
        if (opt.isPresent()) {
            return Response.ok(opt.get()).build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @GET
    @Operation(summary = "Get all Students")
    public Response getAllStudent() {
        Optional<List<Student>> opt = service.getAllStudent();
        if (opt.isPresent()) {
            List<Student> students = opt.get();
            List<StudentDTO> studentsDTO = new ArrayList<>();
            for (Student student : students) {
                studentsDTO.add(StudentDTO.Converter.toStudentDTO(student));
            }
            return Response.ok(studentsDTO).build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @DELETE
    @POST
    public Response notImplemented() {
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }
}
