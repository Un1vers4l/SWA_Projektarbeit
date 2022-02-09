/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-02-09 20:50:36
 * @modify date 2022-02-09 20:50:36
 * @desc [description]
 */
package de.hsos.swa.studiom.ModulManagment.boundary.rest.answer;

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
import de.hsos.swa.studiom.ModulManagment.entity.Answer;
import de.hsos.swa.studiom.shared.dto.DataDto;
import de.hsos.swa.studiom.shared.dto.StatusDto;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;

@RequestScoped
@Transactional
@Path("api/v1/Modul/{moduleId}/Question/{questionId}/Answer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AnswerRest {
    Logger log = Logger.getLogger(AnswerRest.class);

    @Context
    UriInfo uriInfo;

    @Inject
    AnswerService answerService;

    @Inject
    Validator validator;

    @Inject
    JsonWebToken jwt;

    @Operation(summary = "Zeigt alle Answers vom Question an Rechte: {SEKT, STUDENT}")
    @RolesAllowed({"STUDENT", "SEKT"})
    @GET
    public Response getAllAnswer(@PathParam("moduleId") int modulId, @PathParam("questionId") int questionId){
        log.info("GET " +  uriInfo.getPath());
        try {
            List<AnswerDto> aDto = this.answerService.getAllAnswer(modulId, questionId)
                .stream()
                .map(AnswerDto.Converter::SimpleDto)
                .collect(Collectors.toList());

            return Response.ok(new DataDto<AnswerDto>(aDto)).build();

        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @Operation(summary = "Erzeugt eine Answer: {STUDENT}")
    @RolesAllowed({"STUDENT"})
    @POST
    public Response creatAnswer(@PathParam("moduleId") int modulId, @PathParam("questionId") int questionId, PostAnswerDto newAnswer){
        log.info("POST " +  uriInfo.getPath());
        Set<ConstraintViolation<PostAnswerDto>> violations = validator.validate(newAnswer);
        if(!violations.isEmpty()){
            return Response.ok(new StatusDto(violations)).build();
        }
        Integer matNr = Integer.parseInt(jwt.getClaim("matNr").toString());

        try {
            Answer answer = this.answerService.createdAnswer(matNr, modulId, questionId, newAnswer.getText());

            return Response.ok(AnswerDto.Converter.AnswerToDto(answer)).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
