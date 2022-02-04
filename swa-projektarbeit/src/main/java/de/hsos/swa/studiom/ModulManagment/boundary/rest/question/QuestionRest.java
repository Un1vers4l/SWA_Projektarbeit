package de.hsos.swa.studiom.ModulManagment.boundary.rest.question;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.jwt.JsonWebToken;

import de.hsos.swa.studiom.ModulManagment.boundary.dto.question.PostQuestionDto;
import de.hsos.swa.studiom.ModulManagment.boundary.dto.question.QuestionDto;
import de.hsos.swa.studiom.ModulManagment.control.QuestionService;
import de.hsos.swa.studiom.ModulManagment.entity.Question;
import de.hsos.swa.studiom.shared.dto.DataDto;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;

@RequestScoped
@Transactional
@Path("api/v1/Modul/{moduleId}/Question")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class  QuestionRest {
    @Inject
    QuestionService questionService;

    @Inject
    Validator validator;

    @Inject
    JsonWebToken jwt;

    @RolesAllowed({"STUDENT", "SEKT"})
    @GET
    public Response getAllQuestion(@PathParam("moduleId") int modulId){
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

    @RolesAllowed({"STUDENT"})
    @POST
    public Response creatQuestion(@PathParam("moduleId") int modulId, PostQuestionDto newQuestion){
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