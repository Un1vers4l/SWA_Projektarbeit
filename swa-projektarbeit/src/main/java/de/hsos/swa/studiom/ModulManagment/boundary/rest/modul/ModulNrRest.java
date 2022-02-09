/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-02-03 20:18:01
 * @modify date 2022-02-03 20:18:01
 * @desc [description]
 */
package de.hsos.swa.studiom.ModulManagment.boundary.rest.modul;

import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.logging.Logger;

import de.hsos.swa.studiom.ModulManagment.boundary.dto.modul.ModulDto;
import de.hsos.swa.studiom.ModulManagment.boundary.dto.modul.PostModulDto;
import de.hsos.swa.studiom.ModulManagment.control.ModulService;
import de.hsos.swa.studiom.ModulManagment.entity.Modul;
import de.hsos.swa.studiom.shared.dto.StatusDto;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;

@RequestScoped
@Transactional
@Path("api/v1/Modul/{moduleId}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ModulNrRest {
    
    Logger log = Logger.getLogger(ModulNrRest.class);

    @Context
    UriInfo uriInfo;

    @Inject 
    ModulService moduleService;

    @Inject
    Validator validator;

    @Operation(summary = "Zeigt detailliert ein Modul an Rechte: {SEKT, STUDENT}")
    @RolesAllowed({"STUDENT", "SEKT"})
    @GET
    public Response getModule(@PathParam("moduleId") int modulId){
        log.info("GET " +  uriInfo.getPath());
        Optional<Modul> module = moduleService.getModul(modulId);
        
        if(!module.isPresent()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(ModulDto.Converter.ModuleToDto(module.get())).build();
    }

    @Operation(summary = "Verändert ein Modul Rechte: {SEKT}")
    @RolesAllowed("SEKT")
    @PATCH
    public Response patchModule(@PathParam("moduleId") int modulId, PostModulDto patchModul){
        log.info("PATCH " +  uriInfo.getPath());
        try {
            this.moduleService.changeModule(modulId, PostModulDto.Converter.DtoToModul(patchModul));
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(new StatusDto("Aenderungen wurden gespeichert", true)).build();
    }
    @Operation(summary = "Löscht ein Modul Rechte: {SEKT}")
    @RolesAllowed("SEKT")
    @DELETE
    public Response deleteModule(@PathParam("moduleId") int modulId){
        log.info("DELETE " +  uriInfo.getPath());
        if(!this.moduleService.deleteModule(modulId)){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new StatusDto("Modul wurde geloescht", true)).build();
    }
}
