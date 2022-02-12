/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-31 11:57:45
 * @modify date 2022-02-03 09:10:33
 * @desc [description]
 */
package de.hsos.swa.studiom.StudyGroupManagement.boundary.rest.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.logging.Logger;

import de.hsos.swa.studiom.StudyGroupManagement.boundary.dto.GroupDTO;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;
import de.hsos.swa.studiom.StudyGroupManagement.gateway.ProjectRepository;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;
import de.hsos.swa.studiom.shared.exceptions.JoinGroupException;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api/v1/projects")
@Transactional
@RequestScoped
public class ProjectRessource {

    Logger log = Logger.getLogger(ProjectRessource.class);

    @Context
    UriInfo uriInfo;

    @Inject
    ProjectRepository service;

    @GET
    @RolesAllowed("USER")
    @Operation(summary = "Get all Projects")
    public Response getAllProjects() {
        log.info("GET " + uriInfo.getPath());
        Optional<List<Group>> allProjects = service.getAllProjects();
        if (allProjects.isPresent()) {
            List<GroupDTO> gDtos = allProjects.get().stream().map(GroupDTO.Converter::toMinimalGroupDTO)
                    .collect(Collectors.toList());
            return Response.ok(gDtos).build();
        }
        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    }

    @DELETE
    @POST
    public Response notImplementedProjects() {
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }

    @PUT
    @RolesAllowed("STUDENT")
    @Operation(summary = "Creates a new Project", description = "Creates a new Project for the Module if allowed")
    public Response createProject(@QueryParam("matNr") int matNr, @QueryParam("moduleId") int moduleId) {
        log.info("PUT " + uriInfo.getPath());
        try {
            Optional<Group> createProject = service.createProject(matNr, moduleId);
            if (createProject.isPresent()) {
                return Response.ok(GroupDTO.Converter.toSimpleGroupDTO(createProject.get())).build();
            }
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        } catch (EntityNotFoundException | JoinGroupException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

}
