/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-02-09 14:39:44
 * @modify date 2022-02-09 14:39:44
 * @desc [description]
 */
package de.hsos.swa.studiom.ModulManagment.boundary.rest.question;

import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
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

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.logging.Logger;

import de.hsos.swa.studiom.ModulManagment.boundary.dto.question.PostQuestionDto;
import de.hsos.swa.studiom.ModulManagment.boundary.dto.question.QuestionDto;
import de.hsos.swa.studiom.ModulManagment.control.QuestionService;
import de.hsos.swa.studiom.ModulManagment.entity.Question;
import de.hsos.swa.studiom.UserManagement.entity.Role;
import de.hsos.swa.studiom.shared.dto.StatusDto;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;

@RequestScoped
@Transactional
@Path("api/v1/Modul/{moduleId}/Question/{questionId}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class QuestionNrRest {
    Logger log = Logger.getLogger(QuestionNrRest.class);

    @Context
    UriInfo uriInfo;

    @Inject 
    QuestionService questionService;

    @Inject
    JsonWebToken jwt;

    @Operation(summary = "Zeigt detailliert eine Question an Rechte: {STUDENT, SEKT}")
    @RolesAllowed({"STUDENT", "SEKT"})
    @GET
    public Response getQuestion(@PathParam("moduleId") int modulId, @PathParam("questionId") int questionId){
        log.info("GET " +  uriInfo.getPath());
        Optional<Question> qOptional = questionService.getQuestion(modulId, questionId);
        if(!qOptional.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(QuestionDto.Converter.QuestionToDto(qOptional.get())).build();
    }

    @Operation(summary = "Verändert ein Question Rechte: {STUDENT, SEKT}", description = "Achtung Role SEKT darf alle verändern und Student nur seine eigene Question")
    @RolesAllowed({"STUDENT", "SEKT"})
    @PATCH
    public Response patchQuestion(@PathParam("moduleId") int modulId, @PathParam("questionId") int questionId, PostQuestionDto patchQeustion){
        log.info("PATCH " +  uriInfo.getPath());
        try {
            boolean isSekt = jwt.getGroups().contains(Role.SEKT.toString());
            if(!isSekt){
                Integer matNr = Integer.parseInt(jwt.getClaim("matNr").toString());
                boolean isOwner = this.questionService.isQuestionOwner(matNr, modulId, questionId);

                if(!isOwner) return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            
            this.questionService.changeQuestion(modulId, questionId, PostQuestionDto.Converter.dtoToQuestion(patchQeustion));
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(new StatusDto("Aenderungen wurden gespeichert", true)).build();
    }

    @Operation(summary = "Löscht ein Question Rechte: {STUDENT, SEKT}", description = "Achtung Role SEKT darf alle löschen und Student nur seine eigene Question")
    @RolesAllowed({"STUDENT", "SEKT"})
    @DELETE
    public Response deleteQuestion(@PathParam("moduleId") int modulId, @PathParam("questionId") int questionId){
        log.info("DELETE " +  uriInfo.getPath());
        try {
            boolean isSekt = jwt.getGroups().contains(Role.SEKT.toString());
            if(!isSekt){
                Integer matNr = Integer.parseInt(jwt.getClaim("matNr").toString());
                boolean isOwner = this.questionService.isQuestionOwner(matNr, modulId, questionId);

                if(!isOwner) return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        boolean isSuccessful = this.questionService.deleteQuestion(questionId, modulId);

        if(!isSuccessful) return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(new StatusDto("Question wurde geloescht", true)).build();
    }
}
