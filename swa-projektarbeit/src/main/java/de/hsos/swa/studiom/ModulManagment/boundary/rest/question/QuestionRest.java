/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-02-09 14:39:47
 * @modify date 2022-02-09 14:39:47
 * @desc [description]
 */
package de.hsos.swa.studiom.ModulManagment.boundary.rest.question;

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

import de.hsos.swa.studiom.ModulManagment.boundary.dto.question.PostQuestionDto;
import de.hsos.swa.studiom.ModulManagment.boundary.dto.question.QuestionDto;
import de.hsos.swa.studiom.ModulManagment.control.QuestionService;
import de.hsos.swa.studiom.ModulManagment.entity.Question;
import de.hsos.swa.studiom.shared.dto.DataDto;
import de.hsos.swa.studiom.shared.dto.StatusDto;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;

@RequestScoped
@Transactional
@Path("api/v1/Modul/{moduleId}/Question")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class  QuestionRest {
    Logger log = Logger.getLogger(QuestionRest.class);

    @Context
    UriInfo uriInfo;

    @Inject
    QuestionService questionService;

    @Inject
    Validator validator;

    @Inject
    JsonWebToken jwt;

    @Operation(summary = "Zeigt alle Questions vom Modul an Rechte: {SEKT, STUDENT}")
    @RolesAllowed({"STUDENT", "SEKT"})
    @GET
    public Response getAllQuestion(@PathParam("moduleId") int modulId){
        log.info("GET " +  uriInfo.getPath());
        try {
            List<QuestionDto> uDto =  this.questionService.getAllQuestion(modulId)
                .stream()
                .map(QuestionDto.Converter::SimpleDto)
                .collect(Collectors.toList());

            return Response.ok(new DataDto<QuestionDto>(uDto)).build();

        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }   

    @Operation(summary = "Erzeugt eine Question Rechte: {STUDENT}")
    @RolesAllowed({"STUDENT"})
    @POST
    public Response creatQuestion(@PathParam("moduleId") int modulId, PostQuestionDto newQuestion){
        log.info("POST " +  uriInfo.getPath());
        Set<ConstraintViolation<PostQuestionDto>> violations = validator.validate(newQuestion);
        if(!violations.isEmpty()){
            return Response.ok(new StatusDto(violations)).build();
        }

        Integer matNr = Integer.parseInt(jwt.getClaim("matNr").toString());
        try {
            Question question = this.questionService
                .createdQuestion(matNr, modulId, newQuestion.getTopic(), newQuestion.getText());

            return Response.ok(QuestionDto.Converter.QuestionToDto(question)).build();

        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
