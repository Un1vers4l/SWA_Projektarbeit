/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 20:09:50
 * @modify date 2022-01-31 15:36:09
 * @desc [description]
 */
package de.hsos.swa.studiom.StudyGroupManagement.boundary.rest.Group;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudentsManagement.gateway.StudentRepository;
import de.hsos.swa.studiom.StudyGroupManagement.boundary.dto.GroupDTO;
import de.hsos.swa.studiom.StudyGroupManagement.boundary.dto.NewGroupDTO;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;
import de.hsos.swa.studiom.StudyGroupManagement.gateway.GroupRepository;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
// Matnr wird wegen login überflüssig
@Path("/api/v1/group/{groupId}/{matNr}")
@ApplicationScoped
public class GroupGroupIDRessource {
    @Inject
    GroupRepository service;

    @Inject
    StudentRepository studService;

    @GET
    @Operation(summary = "Find Group with Id")
    public Response getGroup(@PathParam("groupId") int groupId) {
        Optional<Group> opt = service.getGroup(groupId);
        if (opt.isPresent()) {
            Group group = opt.get();
            return Response.ok(GroupDTO.Converter.toDTO(group)).build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @POST
    @Operation(summary = "Change a group", description = "Change the name, maxMembers or module of a group")
    public Response changeGroup(@PathParam("groupId") int groupId, NewGroupDTO newGroup) {
        Optional<Student> ownerOpt = studService.getStudent(newGroup.ownerMatNr);
        if (ownerOpt.isPresent()) {
            Optional<Group> changeGroupOpt = service.changeGroup(newGroup.ownerMatNr, groupId,
                    NewGroupDTO.Converter.toGroup(newGroup, ownerOpt.get(), null));
            if (changeGroupOpt.isPresent()) {
                return Response.ok(GroupDTO.Converter.toDTO(changeGroupOpt.get())).build();
            }
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @POST
    @Path("/student")
    @Operation(summary = "Join a Group", description = "Adds a Student to a group if group is not full yet")
    public Response addStudent(@PathParam("matNr") int matNr, @PathParam("groupId") int groupId) {

        Optional<Group> addStudent = service.addStudent(groupId, matNr);
        if (addStudent.isPresent()) {
            return Response.ok(GroupDTO.Converter.toDTO(addStudent.get())).build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @DELETE
    @Operation(summary = "Delete a Group", description = "Deletes a Group, if called by the owner of the group")
    public Response deleteGroup(@PathParam("groupId") int groupId, @PathParam("matNr") int matNr) {
        boolean deleted = service.deleteGroup(matNr, groupId);
        if (deleted) {
            return Response.ok().build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("/student")
    @Operation(summary = "Leave a Group", description = "Leave a Group, if called by a member of the group")
    public Response removeStudent(@PathParam("matNr") int matNr, @PathParam("groupId") int groupId) {
        Optional<Group> removeStudent = service.removeStudent(groupId, matNr);
        if (removeStudent.isPresent()) {
            return Response.ok(GroupDTO.Converter.toDTO(removeStudent.get())).build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }
}
