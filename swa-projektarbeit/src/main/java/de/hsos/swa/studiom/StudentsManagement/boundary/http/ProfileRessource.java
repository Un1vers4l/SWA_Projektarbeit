package de.hsos.swa.studiom.StudentsManagement.boundary.http;


import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.jwt.JsonWebToken;

import de.hsos.swa.studiom.StudentsManagement.boundary.dto.HTTPStudentDTO;
import de.hsos.swa.studiom.StudentsManagement.control.StudentService;
import de.hsos.swa.studiom.StudyGroupManagement.control.ProjectService;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;
import io.quarkus.qute.Template;

@Transactional
@Produces(MediaType.TEXT_HTML)
@Consumes(MediaType.TEXT_HTML)
@Path("/student")
@RolesAllowed("STUDENT")
public class ProfileRessource {
    @Inject
    Template studentModules;

    @Inject
    Template studentGroups;

    @Inject
    StudentService studService;

    @Inject
    ProjectService projectService;

    @Inject
    JsonWebToken jwt;

    @GET
    public Response me() {
        return Response.seeOther(UriBuilder.fromPath("/me/modules").build()).build();
    }

    @GET
    @Path("/me/modules")
    public Response meModules() throws EntityNotFoundException {
        Object claim = jwt.getClaim("matNr");
        if (claim == null) {
            return Response.status(Status.UNAUTHORIZED).build();
        }
        int matNr = Integer.valueOf(claim.toString());
        HTTPStudentDTO stud = HTTPStudentDTO.Converter.toHTTPStudentDTO(studService.getStudent(matNr).get());
        return Response.ok(studentModules.data("student", stud).render()).build();
    }

    @GET
    @Path("/me/groups")
    public Response meGroups() throws EntityNotFoundException {
        Object claim = jwt.getClaim("matNr");
        if (claim == null) {
            return Response.status(Status.UNAUTHORIZED).build();
        }
        int matNr = Integer.valueOf(claim.toString());
        HTTPStudentDTO stud = HTTPStudentDTO.Converter.toHTTPStudentDTO(studService.getStudent(matNr).get());
        return Response.ok(studentGroups.data("student", stud).render()).build();
    }

    @GET
    @Path("/modules/{matNr}")
    public Response studentModules(@PathParam("matNr") int matNr) throws EntityNotFoundException {
        if (matNr == 0) {
            return Response.status(Status.UNAUTHORIZED).build();
        }
        HTTPStudentDTO stud = HTTPStudentDTO.Converter.toHTTPStudentDTO(studService.getStudent(matNr).get());
        return Response.ok(studentModules.data("student", stud).render()).build();
    }

    @GET
    @Path("/groups/{matNr}")
    public Response studentGroups(@PathParam("matNr") int matNr) throws EntityNotFoundException {
        if (matNr == 0) {
            return Response.status(Status.UNAUTHORIZED).build();
        }
        HTTPStudentDTO stud = HTTPStudentDTO.Converter.toHTTPStudentDTO(studService.getStudent(matNr).get());
        return Response.ok(studentGroups.data("student", stud).render()).build();
    }
}
