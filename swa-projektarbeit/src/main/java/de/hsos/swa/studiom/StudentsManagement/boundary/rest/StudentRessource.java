package de.hsos.swa.studiom.StudentsManagement.boundary.rest;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.hsos.swa.studiom.StudentsManagement.control.StudentService;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudentsManagement.gateway.StudentRepository;

@Path("/student")
public class StudentRessource {

    // Named und interface inj
    @Inject
    StudentRepository service;

    @POST
    public Response createStudent(String name) {
        Optional<Student> opt = service.createStudent(name);
        if (opt.isPresent()) {
            return Response.ok(opt.get()).build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }
}
