/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-02-03 20:17:56
 * @modify date 2022-02-03 20:17:56
 * @desc [description]
 */
package de.hsos.swa.studiom.ModulManagment.boundary.rest.modul;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import de.hsos.swa.studiom.ModulManagment.control.ModulService;
import de.hsos.swa.studiom.shared.dto.StatusDto;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;

@RequestScoped
@Transactional
@Path("api/v1/Modul/{moduleId}/Student")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ModulStudentRest {
    
    @Inject 
    ModulService moduleService;

    @Inject
    Validator validator;

    @Inject
    JsonWebToken jwt;

    Logger log = Logger.getLogger(ModulStudentRest.class);

    @POST
    @RolesAllowed("STUDENT")
    public Response addStudent(@PathParam("moduleId") int modulId){
        Integer matNr = Integer.parseInt(jwt.getClaim("matNr").toString());
        boolean success = true;
        try {
            success = this.moduleService.addStudentFromModule(modulId, matNr);
        } catch (EntityNotFoundException e) {
            return Response.ok(new StatusDto(e)).build();
        }
        if(!success){
            return Response.ok(new StatusDto("Du bist schon in diesem Modul")).build();
        }
        return Response.ok(new StatusDto("Du wurde zu diesem Modul hinzugefuegt", true)).build();
    }

    @DELETE
    @RolesAllowed("STUDENT")
    public Response removeStudent(@PathParam("moduleId") int modulId){
        Integer matNr = Integer.parseInt(jwt.getClaim("matNr").toString());
        boolean success = true;
        try {
            success = this.moduleService.removeStudentFromModule(modulId, matNr);
        } catch (EntityNotFoundException e) {
            return Response.ok(new StatusDto(e)).build();
        }
        if(!success){
            return Response.ok(new StatusDto("Du warst nicht in diesem Modul")).build();
        }
        return Response.ok(new StatusDto("Du wurdest aus dem Modul entfernt", true)).build();
    }

}
