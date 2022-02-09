/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 14:42:50
 * @modify date 2022-02-03 09:08:49
 * @desc [description]
 */

package de.hsos.swa.studiom.StudyGroupManagement.boundary.rest.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.logging.Logger;

import de.hsos.swa.studiom.StudyGroupManagement.boundary.dto.GroupDTO;
import de.hsos.swa.studiom.StudyGroupManagement.boundary.dto.NewGroupDTO;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;
import de.hsos.swa.studiom.StudyGroupManagement.gateway.GroupRepository;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
@Path("/api/v1/group")
@RequestScoped
public class GroupRessource {

    Logger log = Logger.getLogger(GroupRessource.class);

    @Context
    UriInfo uriInfo;

    @Inject
    GroupRepository service;

    @POST
    @RolesAllowed("STUDENT")
    @Operation(summary = "Create a new Group")
    public Response createGroup(NewGroupDTO gDTO) {
        log.info("POST " + uriInfo.getPath());
        if (gDTO.maxMember <= 0) {
            return Response.status(Status.BAD_REQUEST).build();
        }
        try {
            Optional<Group> created = service.createGroup(gDTO.ownerMatNr, gDTO.name, gDTO.maxMember, gDTO.moduleId);
            if (created.isPresent()) {
                GroupDTO group = GroupDTO.Converter.toDTO(created.get());
                return Response.ok(group).build();
            }
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @RolesAllowed("USER")
    @Operation(summary = "Find all groups")
    public Response getAllGroups() {
        log.info("GET " + uriInfo.getPath());
        Optional<List<Group>> allGroups = service.getAllGroup();
        if (allGroups.isPresent()) {
            List<GroupDTO> gDTOs = new ArrayList<>();
            for (Group group : allGroups.get()) {
                gDTOs.add(GroupDTO.Converter.toDTO(group));
            }
            return Response.ok(gDTOs).build();
        }
        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    }

    @POST
    @DELETE
    public Response notImplemented() {
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }
}
