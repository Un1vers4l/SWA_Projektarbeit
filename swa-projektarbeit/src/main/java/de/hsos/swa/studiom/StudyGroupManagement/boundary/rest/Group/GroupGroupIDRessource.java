/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 20:09:50
 * @modify date 2022-02-02 08:16:29
 * @desc [description]
 */
package de.hsos.swa.studiom.StudyGroupManagement.boundary.rest.Group;

import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;

import org.jboss.logging.Logger;

import de.hsos.swa.studiom.StudentsManagement.control.StudentService;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudyGroupManagement.boundary.dto.GroupDTO;
import de.hsos.swa.studiom.StudyGroupManagement.boundary.dto.NewGroupDTO;
import de.hsos.swa.studiom.StudyGroupManagement.control.GroupService;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;
import de.hsos.swa.studiom.shared.exceptions.GroupManagementException;
import de.hsos.swa.studiom.shared.exceptions.JoinGroupException;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

@Path("/api/v1/group/{groupId}")
@ApplicationScoped

public class GroupGroupIDRessource {

    Logger log = Logger.getLogger(GroupGroupIDRessource.class);

    @Context
    UriInfo uriInfo;

    @Context
    SecurityContext ctx;

    @Inject
    JsonWebToken jwt;

    @Inject
    GroupService service;

    @Inject
    StudentService studService;

    @GET
    @Operation(summary = "Find Group with Id")
    @RolesAllowed("USER")
    public Response getGroup(@PathParam("groupId") int groupId) {
        try {
            log.info("GET " + uriInfo.getPath());
            Optional<Group> opt = service.getGroup(groupId);
            if (opt.isPresent()) {
                Group group = opt.get();
                return Response.ok(GroupDTO.Converter.toDTO(group)).build();
            }
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @RolesAllowed("STUDENT")
    @Operation(summary = "Change a group", description = "Change the name, maxMembers or module of a group")
    public Response changeGroup(@PathParam("groupId") int groupId, NewGroupDTO newGroup) {
        try {
            log.info("POST " + uriInfo.getPath());
            Optional<Student> ownerOpt = studService.getStudent(newGroup.ownerMatNr);
            if (ownerOpt.isPresent()) {
                Optional<Group> changeGroupOpt = service.changeGroup(newGroup.ownerMatNr, groupId,
                        NewGroupDTO.Converter.toGroup(newGroup, ownerOpt.get(), null));
                if (changeGroupOpt.isPresent()) {
                    return Response.ok(GroupDTO.Converter.toDTO(changeGroupOpt.get())).build();
                }
            }
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        } catch (EntityNotFoundException | GroupManagementException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @RolesAllowed("STUDENT")
    @Path("/student")
    @Operation(summary = "Join a Group", description = "Adds a Student to a group if group is not full yet")
    public Response addStudent(@PathParam("groupId") int groupId) {
        log.info("POST " + uriInfo.getPath());
        Object claim = jwt.getClaim("matNr");
        if (claim == null) {
            return Response.status(Status.UNAUTHORIZED).build();
        }
        int matNr = Integer.valueOf(claim.toString());
        try {
            Optional<Group> addStudent = service.addStudent(groupId, matNr);
            if (addStudent.isPresent()) {
                return Response.ok(GroupDTO.Converter.toDTO(addStudent.get())).build();
            }
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        } catch (JoinGroupException | EntityNotFoundException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @RolesAllowed("STUDENT")
    @Operation(summary = "Delete a Group", description = "Deletes a Group, if called by the owner of the group")
    public Response deleteGroup(@PathParam("groupId") int groupId) {
        log.info("DELETE " + uriInfo.getPath());
        Object claim = jwt.getClaim("matNr");
        if (claim == null) {
            return Response.status(Status.UNAUTHORIZED).build();
        }
        int matNr = Integer.valueOf(claim.toString());
        try {
            boolean deleted = service.deleteGroup(matNr, groupId);
            if (deleted) {
                return Response.ok().build();
            }
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (GroupManagementException e) {
            return Response.status(Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/student")
    @RolesAllowed("STUDENT")
    @Operation(summary = "Leave a Group", description = "Leave a Group, if called by a member of the group")
    public Response removeStudent(@PathParam("groupId") int groupId) {
        log.info("DELETE " + uriInfo.getPath());
        Object claim = jwt.getClaim("matNr");
        if (claim == null) {
            return Response.status(Status.UNAUTHORIZED).build();
        }
        int matNr = Integer.valueOf(claim.toString());
        try {
            Optional<Group> removeStudent = service.removeStudent(groupId, matNr);
            if (removeStudent.isPresent()) {
                return Response.ok(GroupDTO.Converter.toDTO(removeStudent.get())).build();
            }
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
