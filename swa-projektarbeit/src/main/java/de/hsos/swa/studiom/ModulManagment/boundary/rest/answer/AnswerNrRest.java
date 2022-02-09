/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-02-09 20:50:31
 * @modify date 2022-02-09 20:50:31
 * @desc [description]
 */
package de.hsos.swa.studiom.ModulManagment.boundary.rest.answer;

import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.PUT;
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

import de.hsos.swa.studiom.ModulManagment.boundary.dto.answer.AnswerDto;
import de.hsos.swa.studiom.ModulManagment.boundary.dto.answer.PostAnswerDto;
import de.hsos.swa.studiom.ModulManagment.control.AnswerService;
import de.hsos.swa.studiom.ModulManagment.control.QuestionService;
import de.hsos.swa.studiom.ModulManagment.entity.Answer;
import de.hsos.swa.studiom.UserManagement.entity.Role;
import de.hsos.swa.studiom.shared.dto.StatusDto;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;

@RequestScoped
@Transactional
@Path("api/v1/Modul/{moduleId}/Question/{questionId}/Answer/{answerId}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AnswerNrRest {
    Logger log = Logger.getLogger(AnswerNrRest.class);

    @Context
    UriInfo uriInfo;

    @Inject
    AnswerService answerService;

    @Inject
    JsonWebToken jwt;

    @Inject
    QuestionService questionService;

    @Operation(summary = "Zeigt detailliert eine Answer an Rechte: {STUDENT, SEKT}")
    @RolesAllowed({"STUDENT", "SEKT"})
    @GET
    public Response getAnswer(@PathParam("moduleId") int modulId, 
        @PathParam("questionId") int questionId, 
        @PathParam("answerId") int answerId){
        
        log.info("GET " +  uriInfo.getPath());
        Optional<Answer> aOptional = this.answerService.getAnswer(modulId, questionId, answerId);

        if(!aOptional.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(AnswerDto.Converter.AnswerToDto(aOptional.get())).build();
    }

    @Operation(summary = "Verändert ein Answer Rechte: {STUDENT, SEKT}", description = "Achtung Role SEKT darf alle verändern und Student nur seine eigene Question")
    @RolesAllowed({"STUDENT", "SEKT"})
    @PATCH
    public Response patchAnswer(@PathParam("moduleId") int modulId, 
        @PathParam("questionId") int questionId, 
        @PathParam("answerId") int answerId, 
        PostAnswerDto patchAnswer) {
        
        log.info("PATCH " +  uriInfo.getPath());
        try {
            boolean isSekt = jwt.getGroups().contains(Role.SEKT.toString());
            if(!isSekt){
                Integer matNr = Integer.parseInt(jwt.getClaim("matNr").toString());
                boolean isOwner = this.answerService.isAnswerOwner(matNr, modulId, questionId, answerId);

                if(!isOwner) return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            this.answerService.changeAnswer(modulId,
                                            questionId, 
                                            answerId,
                                            PostAnswerDto.Converter.DtoToAnswer(patchAnswer));

        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(new StatusDto("Aenderungen wurden gespeichert", true)).build();
    }
    @Operation(summary = "Löscht ein Answer Rechte: {STUDENT, SEKT}", description = "Achtung Role SEKT darf alle löschen und Student nur seine eigene Question")
    @RolesAllowed({"STUDENT", "SEKT"})
    @DELETE
    public Response deleteAnswer(@PathParam("moduleId") int modulId, 
        @PathParam("questionId") int questionId, 
        @PathParam("answerId") int answerId){
        
        log.info("DELETE " +  uriInfo.getPath());
        try {
            boolean isSekt = jwt.getGroups().contains(Role.SEKT.toString());
            if(!isSekt){
                Integer matNr = Integer.parseInt(jwt.getClaim("matNr").toString());
                boolean isOwner = this.answerService.isAnswerOwner(matNr, modulId, questionId, answerId);

                if(!isOwner) return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        boolean isSuccessful = this.answerService.deleteAnswer(modulId, questionId, answerId);

        if(!isSuccessful) return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(new StatusDto("Answer wurde geloescht", true)).build();
    }

    @Operation(summary = "Kann eine Answer als Solution gestzt werden Rechte: {STUDENT}", description = "Nur der Ersteller der Question darf die Antwort auf Solution setzten. Wenn es bereits eine Solution gibt schlägt dieser fehl.")
    @RolesAllowed("STUDENT")
    @PUT
    public Response changIsSolution(@PathParam("moduleId") int modulId, 
        @PathParam("questionId") int questionId, 
        @PathParam("answerId") int answerId){
        
        log.info("PUT " +  uriInfo.getPath());
        try {
            Integer matNr = Integer.parseInt(jwt.getClaim("matNr").toString());
            boolean isOwner = this.questionService.isQuestionOwner(matNr, modulId, questionId);

            if(!isOwner) return Response.status(Response.Status.UNAUTHORIZED).build();

        } catch (EntityNotFoundException e1) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        boolean isSuccessful;
        try {
            isSuccessful = this.answerService.changeIsSolution(modulId, questionId, answerId);
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if(!isSuccessful) return Response.ok(new StatusDto("Es gibt schon eine Solution")).build();

        return Response.ok(new StatusDto("Die Solution der Antwort wurde geändert", true)).build();
    }
}
