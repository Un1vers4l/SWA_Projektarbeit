/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-02-07 09:02:36
 * @modify date 2022-02-09 19:27:10
 * @desc [description]
 */
package de.hsos.swa.studiom.StudyGroupManagement.boundary.http;

import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.jwt.JsonWebToken;

import de.hsos.swa.studiom.StudentsManagement.boundary.dto.Student.StudentDTO;
import de.hsos.swa.studiom.StudentsManagement.control.StudentService;
import de.hsos.swa.studiom.StudyGroupManagement.boundary.dto.GroupDTO;
import de.hsos.swa.studiom.StudyGroupManagement.boundary.dto.StudentTempDTO;
import de.hsos.swa.studiom.StudyGroupManagement.control.GroupService;
import de.hsos.swa.studiom.StudyGroupManagement.control.ProjectService;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;
import de.hsos.swa.studiom.shared.exceptions.JoinGroupException;
import de.hsos.swa.studiom.shared.exceptions.OwnerException;
import io.quarkus.qute.Template;

@Produces(MediaType.TEXT_HTML)
@Consumes(MediaType.TEXT_HTML)
@Path("/groups")
@Transactional
@RolesAllowed("STUDENT")
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
    public Response groups() {
        Object claim = jwt.getClaim("matNr");
        if (claim == null) {
            return Response.status(Status.UNAUTHORIZED).build();
        }
        try {
            int matNr = Integer.valueOf(claim.toString());
            StudentDTO stud = StudentDTO.Converter.toHTTPStudentDTO(studService.getStudent(matNr).get());
            return Response
                    .ok(groups.data("student", stud).data("groupDetail", null).data("inGroup", false)
                            .data("error", "error")
                            .render())
                    .build();
        } catch (EntityNotFoundException e) {
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{groupId}")
    public Response getGroups(@PathParam("groupId") int groupId,
            @DefaultValue("error") @QueryParam("error") String error) {
        System.out.println("ERROR " + error);
        Object claim = jwt.getClaim("matNr");
        if (claim == null) {
            return Response.status(Status.UNAUTHORIZED).build();
        }
        int matNr = Integer.valueOf(claim.toString());
        StudentDTO stud;
        try {
            stud = StudentDTO.Converter.toHTTPStudentDTO(studService.getStudent(matNr).get());
        } catch (EntityNotFoundException e) {
            return Response.status(Status.BAD_REQUEST).build();
        }
        Group group = getGroup(groupId);
        if (group == null) {
            return Response
                    .ok(groups.data("student", stud).data("groupDetail", null).data("inGroup", false)
                            .data("error", "group not found").render())
                    .build();
        }
        GroupDTO groupDTO = GroupDTO.Converter.toHTTPGroupDTO(group);
        boolean inGroup = false;
        for (StudentTempDTO member : groupDTO.member) {
            if (member.matNr == matNr) {
                inGroup = true;
            }
        }
        System.out.println("ERROR " + error);
        return Response.ok(groups.data("student", stud).data("groupDetail", groupDTO).data("inGroup", inGroup)
                .data("error", error).render()).build();
    }

    @POST
    @Path("/{groupId}/join")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response joinGroup(@PathParam("groupId") int groupId) {
        Object claim = jwt.getClaim("matNr");
        if (claim == null) {
            return Response.status(Status.UNAUTHORIZED).build();
        }
        int matNr = Integer.valueOf(claim.toString());
        Group group = getGroup(groupId);
        if (group == null) {
            return Response.seeOther(UriBuilder.fromPath("/groups/" + groupId).build()).build();
        }
        String error = "";
        switch (group.getType()) {
            case PROJECT:
                try {
                    System.out.println("PROJECTS");
                    projectService.addStudent(matNr, groupId);
                } catch (JoinGroupException | EntityNotFoundException e) {
                    error = e.getMessage();
                }
                break;
            case STUDYGROUP:
                try {
                    groupService.addStudent(groupId, matNr);
                } catch (JoinGroupException | EntityNotFoundException e) {
                    error = e.getMessage();
                }
                break;
        }
        return Response.seeOther(UriBuilder.fromPath("/groups/" + groupId).queryParam("error", error).build()).build();
    }

    @POST
    @Path("/{groupId}/leave")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response leaveGroup(@PathParam("groupId") int groupId) {
        Object claim = jwt.getClaim("matNr");
        if (claim == null) {
            return Response.status(Status.UNAUTHORIZED).build();
        }
        int matNr = Integer.valueOf(claim.toString());
        Group group = getGroup(groupId);
        if (group == null) {
            return Response.seeOther(UriBuilder.fromPath("/groups/" + groupId).build()).build();
        }
        String error = "";
        switch (group.getType()) {
            case PROJECT:
                error = "You can not leave a Project!";
                break;
            case STUDYGROUP:
                try {
                    groupService.removeStudent(groupId, matNr);
                } catch (EntityNotFoundException | OwnerException e) {
                    error = e.getMessage();
                }
                break;
        }
        return Response.seeOther(UriBuilder.fromPath("/groups/" + groupId).queryParam("error", error).build()).build();
    }

    private Group getGroup(int groupId) {
        try {
            Optional<Group> group = groupService.getGroup(groupId);
            if (group.isPresent()) {
                return group.get();
            }
            Optional<Group> project;
            project = projectService.getProject(groupId);
            if (project.isPresent()) {
                return project.get();
            }
        } catch (EntityNotFoundException e) {

        }
        return null;
    }
}
