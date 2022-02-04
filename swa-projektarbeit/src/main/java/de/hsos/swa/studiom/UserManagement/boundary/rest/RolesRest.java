/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-01-31 15:10:43
 * @modify date 2022-01-31 15:10:43
 * @desc [description]
 */
package de.hsos.swa.studiom.UserManagement.boundary.rest;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.logging.Logger;

import de.hsos.swa.studiom.UserManagement.boundary.dto.RoleDto;

@RequestScoped
@Path("api/v1/Role")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RolesRest {

    Logger log = Logger.getLogger(RolesRest.class);

    @RolesAllowed("ADMIN")
    @GET
    @Operation(summary = "Gibt Alle Rolle aus Rechte: {ADMIN}")
    public Response getAllRole(){
        return Response.ok(new RoleDto()).build();
    }
}