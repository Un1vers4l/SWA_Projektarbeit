package de.hsos.swa.studiom.StudyGroupManagement.boundary.http;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.quarkus.qute.Template;

@Produces(MediaType.TEXT_HTML)
@Consumes(MediaType.TEXT_HTML)
@Path("/groups")
public class GroupsRessource {
    @Inject
    Template groups;

    @GET
    public Response groups() {
        return Response.ok(groups.render()).build();
    }
}
