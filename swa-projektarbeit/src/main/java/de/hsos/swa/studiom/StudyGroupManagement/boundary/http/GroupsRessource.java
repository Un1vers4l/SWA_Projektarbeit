package de.hsos.swa.studiom.StudyGroupManagement.boundary.http;

import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.jwt.JsonWebToken;

import de.hsos.swa.studiom.StudentsManagement.boundary.dto.HTTPGroup;
import de.hsos.swa.studiom.StudentsManagement.boundary.dto.HTTPStudentDTO;
import de.hsos.swa.studiom.StudentsManagement.control.StudentService;
import de.hsos.swa.studiom.StudyGroupManagement.control.GroupService;
import de.hsos.swa.studiom.StudyGroupManagement.control.ProjectService;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;
import io.quarkus.qute.Template;

@Produces(MediaType.TEXT_HTML)
@Consumes(MediaType.TEXT_HTML)
@Path("/groups")
@Transactional
public class GroupsRessource {
    @Inject
    Template groups;

    @Inject
    StudentService studService;

    @Inject
    ProjectService projectService;

    @Inject
    GroupService groupService;

    @Inject
    JsonWebToken jwt;

    @GET
    public Response groups() throws EntityNotFoundException {
        Object claim = jwt.getClaim("matNr");
        if (claim == null) {
            return Response.status(Status.UNAUTHORIZED).build();
        }
        int matNr = Integer.valueOf(claim.toString());
        HTTPStudentDTO stud = HTTPStudentDTO.Converter.toHTTPStudentDTO(studService.getStudent(matNr).get());
        return Response.ok(groups.data("student", stud).data("groupDetail", null).render()).build();
    }

    @GET
    @Path("/{groupId}")
    public Response getGroup(@PathParam("groupId") int groupId) throws EntityNotFoundException {
        System.out.println("OK");
        Object claim = jwt.getClaim("matNr");
        if (claim == null) {
            return Response.status(Status.UNAUTHORIZED).build();
        }
        int matNr = Integer.valueOf(claim.toString());
        HTTPStudentDTO stud = HTTPStudentDTO.Converter.toHTTPStudentDTO(studService.getStudent(matNr).get());
        HTTPGroup group = new HTTPGroup();
        Optional<Group> opt = groupService.getGroup(groupId);
        if (!opt.isPresent()) {
            opt = projectService.getProject(groupId);
            if (!opt.isPresent()) {
                return Response.ok(groups.data("student", stud).data("groupDetail", null).render()).build();
            }
        }
        group = HTTPGroup.Converter.toDTO(opt.get());
        return Response.ok(groups.data("student", stud).data("groupDetail", group).render()).build();
    }
}
