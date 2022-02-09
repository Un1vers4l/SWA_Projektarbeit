/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-02-03 20:17:58
 * @modify date 2022-02-03 20:17:58
 * @desc [description]
 */
package de.hsos.swa.studiom.ModulManagment.boundary.rest.modul;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.logging.Logger;

import de.hsos.swa.studiom.ModulManagment.boundary.dto.modul.ModulDto;
import de.hsos.swa.studiom.ModulManagment.boundary.dto.modul.PostModulDto;
import de.hsos.swa.studiom.ModulManagment.control.ModulService;
import de.hsos.swa.studiom.ModulManagment.entity.Modul;
import de.hsos.swa.studiom.shared.dto.DataDto;
import de.hsos.swa.studiom.shared.dto.StatusDto;

@RequestScoped
@Transactional
@Path("api/v1/Modul")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ModulRest {
    
    Logger log = Logger.getLogger(ModulRest.class);

    @Context
    UriInfo uriInfo;

    @Inject 
    ModulService moduleService;

    @Inject
    Validator validator;

    @Inject
    JsonWebToken jwt;

    @Operation(summary = "Zeigt alle Moduls an Rechte: {SEKT, STUDENT}")
    @RolesAllowed({"STUDENT", "SEKT"})
    @GET
    public Response getAllModul(){
        log.info("GET " +  uriInfo.getPath());
        List<ModulDto> list = moduleService.getAllModul()
        .stream()
        .map(ModulDto.Converter::SimpleDto)
        .collect(Collectors.toList());
  
        return Response.ok(new DataDto<ModulDto>(list)).build();
    }
    @Operation(summary = "Erstellt ein Modul Rechte: {SEKT}")
    @POST
    @RolesAllowed("SEKT")
    public Response creatModule(PostModulDto newModul){
        log.info("POST " +  uriInfo.getPath());
        Set<ConstraintViolation<PostModulDto>> violations = validator.validate(newModul);
        if(!violations.isEmpty()){
            return Response.ok(new StatusDto(violations)).build();
        }

        Modul module = this.moduleService.createdModule(newModul.getName(), newModul.getDescription(), newModul.getIsProject());
        return Response.ok(ModulDto.Converter.ModuleToDto(module)).build();
    }
    
}
