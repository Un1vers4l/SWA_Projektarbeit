/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-31 10:51:27
 * @modify date 2022-01-31 11:55:57
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

import org.eclipse.microprofile.openapi.annotations.Operation;

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
@Path("/api/v1/projects/projectId/{projectId}/{matNr}")
@ApplicationScoped
public class ProjectIdRessource {
    @Inject
    ProjectRepository service;

    @DELETE
    @Operation(summary = "Delete a project", description = "Deletes a project, if you are the owner and no one else joined")
    public Response deleteProject(@PathParam("matNr") int matNr, @PathParam("projectId") int projectId) {
        boolean deleted = service.deleteProject(matNr, projectId);
        if (deleted) {
            return Response.ok("true").build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @GET
    @Operation(summary = "Finds a project with Id")
    public Response getProject(@PathParam("projectId") int projectId) {
        Optional<Group> project = service.getProject(projectId);
        if (project.isPresent()) {
            return Response.ok(GroupDTO.Converter.toDTO(project.get())).build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @PUT
    @Operation(summary = "Join a Project", description = "Adds a Student to a project if not already joined another project in that module and if project is not full yet")
    public Response addStudentToProject(@PathParam("matNr") int matNr, @PathParam("projectId") int projectId) {
        Optional<Group> group = service.addStudent(matNr, projectId);
        if (group.isPresent()) {
            return Response.ok(GroupDTO.Converter.toDTO(group.get())).build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @POST
    public Response notImplementedProjectsProjectId() {
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }
}