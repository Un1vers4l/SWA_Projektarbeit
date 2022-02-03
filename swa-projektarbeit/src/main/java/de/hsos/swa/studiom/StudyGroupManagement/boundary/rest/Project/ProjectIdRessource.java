/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-31 10:51:27
 * @modify date 2022-02-02 08:22:04
 * @desc [description]
 */
package de.hsos.swa.studiom.StudyGroupManagement.boundary.rest.Project;

import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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

import de.hsos.swa.studiom.StudyGroupManagement.boundary.dto.GroupDTO;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;
import de.hsos.swa.studiom.StudyGroupManagement.gateway.ProjectRepository;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;
import de.hsos.swa.studiom.shared.exceptions.GroupManagementException;
import de.hsos.swa.studiom.shared.exceptions.JoinGroupException;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api/v1/projects/projectId/{projectId}")
@RequestScoped
public class ProjectIdRessource {

    Logger log = Logger.getLogger(ProjectIdRessource.class);

    @Context
    UriInfo uriInfo;

    @Inject
    ProjectRepository service;

    @Context
    SecurityContext ctx;

    @Inject
    JsonWebToken jwt;

    @DELETE
    @RolesAllowed("Student")
    @Operation(summary = "Delete a project", description = "Deletes a project, if you are the owner and no one else joined")
    public Response deleteProject(@PathParam("projectId") int projectId) {
        log.info("DELETE " + uriInfo.getPath());
        Object claim = jwt.getClaim("matNr");
        if (claim == null) {
            return Response.status(Status.UNAUTHORIZED).build();
        }
        int matNr = Integer.valueOf(claim.toString());
        try {
            boolean deleted = service.deleteProject(matNr, projectId);
            if (deleted) {
                return Response.ok("true").build();
            }
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (GroupManagementException e) {
            return Response.status(Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    @GET
    @RolesAllowed("USER")
    @Operation(summary = "Finds a project with Id")
    public Response getProject(@PathParam("projectId") int projectId) {
        try {
            log.info("GET " + uriInfo.getPath());
            Optional<Group> project = service.getProject(projectId);
            if (project.isPresent()) {
                return Response.ok(GroupDTO.Converter.toDTO(project.get())).build();
            }
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @RolesAllowed("STUDENT")
    @Operation(summary = "Join a Project", description = "Adds a Student to a project if not already joined another project in that module and if project is not full yet")
    public Response addStudentToProject(@PathParam("projectId") int projectId) {
        log.info("PUT " + uriInfo.getPath());
        Object claim = jwt.getClaim("matNr");
        if (claim == null) {
            return Response.status(Status.UNAUTHORIZED).build();
        }
        int matNr = Integer.valueOf(claim.toString());
        try {
            Optional<Group> group = service.addStudent(matNr, projectId);
            if (group.isPresent()) {
                return Response.ok(GroupDTO.Converter.toDTO(group.get())).build();
            }
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        } catch (EntityNotFoundException | JoinGroupException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    public Response notImplementedProjectsProjectId() {
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }
}