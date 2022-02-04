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
@Path("/index")
public class IndexRessource {
    @Inject
    Template index;

    @GET
    public Response index() {
        return Response.ok(index.render()).build();
    }
}
