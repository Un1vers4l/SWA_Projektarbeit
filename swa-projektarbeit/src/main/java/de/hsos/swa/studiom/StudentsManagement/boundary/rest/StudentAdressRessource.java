/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 14:42:32
 * @modify date 2022-02-01 08:13:39
 * @desc [description]
 */
package de.hsos.swa.studiom.StudentsManagement.boundary.rest;

import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
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
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.logging.Logger;
import de.hsos.swa.studiom.StudentsManagement.boundary.dto.AdressDTO;
import de.hsos.swa.studiom.StudentsManagement.entity.Adress;
import de.hsos.swa.studiom.StudentsManagement.gateway.AdressRepository;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api/v1/student/{matNr}/adress")
@ApplicationScoped
public class StudentAdressRessource {

    Logger log = Logger.getLogger(StudentAdressRessource.class);

    @Context
    UriInfo uriInfo;

    @Inject
    AdressRepository adressService;

    @GET
    @Operation(summary = "Find an adress", description = "Find the adress of a student with their matNr")
    public Response getAdress(@PathParam("matNr") int matNr){
        try {
            log.info("GET " + uriInfo.getPath());
            Optional<Adress> opt = adressService.getAdress(matNr);
            if (opt.isPresent()) {
                return Response.ok(AdressDTO.Converter.toDto(opt.get())).build();
            }
            return Response.status(Status.BAD_REQUEST).build();
        } catch (EntityNotFoundException enf) {
            return Response.status(Status.BAD_REQUEST).entity(enf.getMessage()).build();
        }
    }

    @PUT
    @Operation(summary = "Create a new adress", description = "Create a new adress for a student")
    public Response createAdress(@PathParam("matNr") int matNr, AdressDTO adress) {
        log.info("PUT " + uriInfo.getPath());
        Optional<Adress> opt = adressService.createAdress(matNr, AdressDTO.Converter.toAdress(adress));
        if (opt.isPresent()) {
            return Response.ok(AdressDTO.Converter.toDto(opt.get())).build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @POST
    @Operation(summary = "Change an adress", description = "Change the adress of a student")
    public Response changeAdress(@PathParam("matNr") int matNr, AdressDTO adress) {
        log.info("POST " + uriInfo.getPath());
        Optional<Adress> opt = adressService.changeAdress(matNr, AdressDTO.Converter.toAdress(adress));
        if (opt.isPresent()) {
            return Response.ok(AdressDTO.Converter.toDto(opt.get())).build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @DELETE
    @Operation(summary = "Delete an adress", description = "Delete a students adress")
    public Response deleteAdress(@PathParam("matNr") int matNr) {
        log.info("DELETE " + uriInfo.getPath());
        boolean deleted = adressService.deleteAdress(matNr);
        if (deleted) {
            return Response.ok().build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }
}
