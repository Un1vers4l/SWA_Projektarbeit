package de.hsos.swa.studiom.StudentsManagement.boundary.http;

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
@Path("/me")
public class ProfileRessource {
    @Inject
    Template me;

    @GET
    public Response me() {
        return Response.ok(me.render()).build();
    }
}
