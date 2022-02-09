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

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
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
import de.hsos.swa.studiom.StudentsManagement.control.StudentService;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.UserManagement.control.UserService;
import de.hsos.swa.studiom.shared.dto.StatusDto;
import de.hsos.swa.studiom.shared.exceptions.CanNotGeneratUserExeption;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api/v1/student")
@RequestScoped
@RolesAllowed("SEKT")
@Transactional
public class StudentRessource {

    Logger log = Logger.getLogger(StudentRessource.class);

    @Context
    UriInfo uriInfo;

    @Inject
    StudentService service;

    @POST
    @Operation(summary = "Create a new student", description = "Create a new student with their name")
    public Response createStudent(newStudentDTO newStudent) {
        log.info("POST " + uriInfo.getPath());
        Optional<Student> opt = Optional.ofNullable(null);
        try {
            opt = service.createStudent(newStudent.getVorname(), newStudent.getNachname());
        } catch (CanNotGeneratUserExeption e) {
            return Response.ok(new StatusDto(e)).build();
        }
        if (opt.isPresent()) {
            return Response.ok(opt.get()).build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @GET
    @Operation(summary = "Get all Students")
    public Response getAllStudent() {
        log.info("GET " + uriInfo.getPath());
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
    @PUT
    public Response notImplemented() {
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }
}
