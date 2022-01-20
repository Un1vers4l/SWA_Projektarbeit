package de.hsos.swa.studiom.StudentsManagement.boundary.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.hsos.swa.studiom.StudentsManagement.boundary.dto.StudentDTO;
import de.hsos.swa.studiom.StudentsManagement.control.StudentService;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudentsManagement.gateway.StudentRepository;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/student")
@ApplicationScoped
public class StudentRessource {

    // Named und interface inj
    @Inject
    StudentRepository service;

    @PUT
    public Response createStudent(String name) {
        Optional<Student> opt = service.createStudent(name);
        if (opt.isPresent()) {
            return Response.ok(opt.get()).build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @POST
    public Response changeStudent(StudentDTO studentDTO) {
        /*
         * Optional<Student> opt =
         * service.changeStudent(StudentDTO.Converter.toStudent(studentDTO));
         * if (opt.isPresent()) {
         * return Response.ok(opt.get()).build();
         * }
         * return Response.status(Status.BAD_REQUEST).build();
         */
        return null;
    }

    @GET
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
}
