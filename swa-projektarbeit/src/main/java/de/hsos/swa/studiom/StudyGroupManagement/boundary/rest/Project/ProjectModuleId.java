/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-31 11:25:10
 * @modify date 2022-01-31 11:57:39
 * @desc [description]
 */
package de.hsos.swa.studiom.StudyGroupManagement.boundary.rest.Project;

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
import de.hsos.swa.studiom.StudyGroupManagement.gateway.ProjectRepository;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api/v1/projects/{moduleId}/{matNr}")
@ApplicationScoped
public class ProjectModuleId {

    @Inject
    ProjectRepository service;

    @PUT
    public Response createProject(@PathParam("matNr") int matNr, @PathParam("moduleId") int moduleId) {
        Optional<Group> createProject = service.createProject(matNr, moduleId);
        if (createProject.isPresent()) {
            return Response.ok(GroupDTO.Converter.toDTO(createProject.get())).build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @GET
    @POST
    @DELETE
    public Response notImplementedProjectsModuleId() {
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }
}
