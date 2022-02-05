package de.hsos.swa.studiom.StudentsManagement.boundary.http;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import de.hsos.swa.studiom.StudentsManagement.boundary.dto.HTTPStudentDTO;
import de.hsos.swa.studiom.StudentsManagement.control.StudentService;
import de.hsos.swa.studiom.StudyGroupManagement.control.ProjectService;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;
import io.quarkus.qute.Template;

@Transactional
@Produces(MediaType.TEXT_HTML)
@Consumes(MediaType.TEXT_HTML)
@Path("/me")
public class ProfileRessource {
    @Inject
    Template meModules;

    @Inject
    Template meGroups;

    @Inject
    StudentService studService;

    @Inject
    ProjectService projectService;

    @GET
    public Response me(@DefaultValue("1000") @QueryParam("matNr") int matNr) {
        return Response.seeOther(UriBuilder.fromPath("/index/kunde/").queryParam("matNr", matNr).build()).build();
    }

    @GET
    @Path("/modules")
    public Response meModules(@DefaultValue("1000") @QueryParam("matNr") int matNr) throws EntityNotFoundException {
        HTTPStudentDTO stud = HTTPStudentDTO.Converter.toHTTPStudentDTO(studService.getStudent(matNr).get());
        return Response.ok(meModules.data("student", stud).render()).build();
    }

    @GET
    @Path("/groups")
    public Response meGroups(@DefaultValue("1000") @QueryParam("matNr") int matNr) throws EntityNotFoundException {
        HTTPStudentDTO stud = HTTPStudentDTO.Converter.toHTTPStudentDTO(studService.getStudent(matNr).get());
        return Response.ok(meGroups.data("student", stud).render()).build();
    }
}
