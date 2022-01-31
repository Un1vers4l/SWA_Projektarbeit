/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 14:42:50
 * @modify date 2022-01-22 20:09:57
 * @desc [description]
 */
package de.hsos.swa.studiom.StudyGroupManagement.boundary.rest.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.hsos.swa.studiom.StudentsManagement.boundary.dto.AdressDTO;
import de.hsos.swa.studiom.StudentsManagement.control.AddressService;
import de.hsos.swa.studiom.StudentsManagement.entity.Adress;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudentsManagement.gateway.StudentRepository;
import de.hsos.swa.studiom.StudyGroupManagement.boundary.dto.GroupDTO;
import de.hsos.swa.studiom.StudyGroupManagement.boundary.dto.NewGroupDTO;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;
import de.hsos.swa.studiom.StudyGroupManagement.gateway.GroupRepository;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api/v1/group")
@ApplicationScoped
public class GroupRessource {

    @Inject
    GroupRepository service;

    @PUT
    public Response createGroup(NewGroupDTO gDTO) {
        Optional<Group> created = service.createGroup(gDTO.ownerMatNr, gDTO.name, gDTO.maxMember, gDTO.moduleId);
        if (created.isPresent()) {
            NewGroupDTO group = NewGroupDTO.Converter.toDTO(created.get());
            return Response.ok(group).build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @GET
    public Response getAllGroups() {
        Optional<List<Group>> allGroups = service.getAllGroup();
        if (allGroups.isPresent()) {
            List<GroupDTO> gDTOs = new ArrayList<>();
            for (Group group : allGroups.get()) {
                gDTOs.add(GroupDTO.Converter.toDTO(group));
            }
            return Response.ok(gDTOs).build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @POST
    @DELETE
    public Response notImplemented() {
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }
}
