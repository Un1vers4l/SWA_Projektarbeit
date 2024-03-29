/**
 * @author Joana Wegener (855518)
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-02-12 18:44:47
 * @modify date 2022-02-12 18:44:47
 * @desc [description]
 */
package de.hsos.swa.studiom.ModulManagment.boundary.http;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.eclipse.microprofile.jwt.JsonWebToken;

import de.hsos.swa.studiom.ModulManagment.boundary.dto.modul.ModulDto;
import de.hsos.swa.studiom.ModulManagment.control.AnswerService;
import de.hsos.swa.studiom.ModulManagment.control.ModulService;
import de.hsos.swa.studiom.ModulManagment.control.QuestionService;
import de.hsos.swa.studiom.StudentsManagement.boundary.dto.Student.StudentDTO;
import de.hsos.swa.studiom.StudentsManagement.control.StudentService;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;
import io.quarkus.qute.Template;

@Transactional
@RequestScoped
@Produces(MediaType.TEXT_HTML)
@Consumes(MediaType.TEXT_HTML)
@RolesAllowed("STUDENT")
@Path("modules/{moduleId}/forum")
public class ModuleForumRessource {
    @Inject
    Template modulesForum;

    @Inject
    Template modulesInfo;

    @Inject
    ModulService moduleService;

    @Inject
    StudentService studService;

    @Inject
    QuestionService questService;

    @Inject
    AnswerService answerService;

    @Inject
    JsonWebToken jwt;

    @GET
    public Response getDetailForum(@PathParam("moduleId") int moduleId,
            @DefaultValue("error") @QueryParam("error") String error) {
        StudentDTO student;
        boolean inModule = false;
        try {
            int matNr = Integer.valueOf(jwt.getClaim("matNr").toString());
            inModule = moduleService.isInModule(matNr, moduleId);
            student = StudentDTO.Converter.toHTTPStudentDTO(studService.getStudent(matNr).get());
        } catch (EntityNotFoundException e) {
            student = null;
            error = e.getMessage();
        }
        ModulDto moduleDetail = ModulDto.Converter.toDetailHTTPModule(moduleService.getModul(moduleId).get());

        return Response
                .ok(modulesForum.data("student", student).data("moduleDetail", moduleDetail).data("inModule", inModule)
                        .data("error", error)
                        .render())
                .build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/addAnswer")
    public Response submitAnswer(@PathParam("moduleId") int moduleId, @FormParam("questionId") int questionId,
            @FormParam("answer") String answer) {
        String error = "error";
        try {
            answerService.createdAnswer(Integer.valueOf(jwt.getClaim("matNr").toString()), moduleId, questionId,
                    answer);
        } catch (EntityNotFoundException e) {
            error = e.getMessage();
        }
        return Response
                .seeOther(UriBuilder.fromPath("/modules/" + moduleId + "/forum").queryParam("error", error).build())
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/question")
    public Response submitQuestion(@PathParam("moduleId") int moduleId, @FormParam("topic") String topic,
            @FormParam("question") String question) {
        String error = "error";
        try {
            questService.createdQuestion(Integer.valueOf(jwt.getClaim("matNr").toString()), moduleId, topic, question);
        } catch (EntityNotFoundException e) {
            error = e.getMessage();
        }
        return Response
                .seeOther(UriBuilder.fromPath("/modules/" + moduleId + "/forum").queryParam("error", error).build())
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/deleteQuestion")
    public Response deleteQuestion(@PathParam("moduleId") int moduleId, @FormParam("questionId") int questionId) {
        String error = "error";
        try {
            if (questService.isQuestionOwner(Integer.valueOf(jwt.getClaim("matNr").toString()), moduleId,
                    questionId) == true) {
                if (questService.deleteQuestion(questionId, moduleId) == false) {
                    error = "Question could not be deleted!";
                }
            } else {
                error = "You are not the Owner of the Question";
            }
        } catch (EntityNotFoundException e) {
            error = "Question could not be deleted!";
        }
        return Response
                .seeOther(UriBuilder.fromPath("/modules/" + moduleId + "/forum").queryParam("error", error).build())
                .build();
    }
}
