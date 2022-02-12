package de.hsos.swa.studiom.ModulManagment.boundary.http;

import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.print.attribute.standard.Media;
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
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.jwt.JsonWebToken;

import de.hsos.swa.studiom.ModulManagment.boundary.dto.answer.AnswerDto;
import de.hsos.swa.studiom.ModulManagment.boundary.dto.question.QuestionDto;
import de.hsos.swa.studiom.ModulManagment.boundary.http.dto.HTTPModulDTO;
import de.hsos.swa.studiom.ModulManagment.control.AnswerService;
import de.hsos.swa.studiom.ModulManagment.control.ModulService;
import de.hsos.swa.studiom.ModulManagment.control.QuestionService;
import de.hsos.swa.studiom.ModulManagment.entity.Answer;
import de.hsos.swa.studiom.ModulManagment.entity.Modul;
import de.hsos.swa.studiom.ModulManagment.entity.Question;
import de.hsos.swa.studiom.StudentsManagement.boundary.dto.Student.StudentDTO;
import de.hsos.swa.studiom.StudentsManagement.control.StudentService;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateExtension.TemplateAttribute;

@Transactional
@RequestScoped
@Produces(MediaType.TEXT_HTML)
@Consumes(MediaType.TEXT_HTML)
@RolesAllowed("STUDENT")
@Path("modules/{moduleId}/students")
public class ModuleStudentsRessource {

    @Inject
    Template modulesStudents;

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
    public Response getModuleStudents(@PathParam("moduleId") int moduleId,
            @DefaultValue("error") @QueryParam("error") String error) {
       StudentDTO student;
        try {
            student = getHttpStudentDTO();
        } catch (EntityNotFoundException e) {
            student = null;
            error = e.getMessage();
        }
        HTTPModulDTO moduleDetail = getHttpModulDTO(moduleId);
        return Response
                .ok(modulesStudents.data("student", student).data("moduleDetail", moduleDetail).data("groups", null)
                        .data("error", error).data("inModule", true)
                        .render())
                .build();
    }



    private StudentDTO getHttpStudentDTO() throws EntityNotFoundException {
        Object claim = jwt.getClaim("matNr");
        if (claim == null) {
            return null;
        }
        int matNr = Integer.valueOf(claim.toString());
        Optional<Student> opt;
        opt = studService.getStudent(matNr);
        if (opt.isPresent()) {
            return StudentDTO.Converter.toHTTPStudentDTO(opt.get());
        }
        return null;
    }

    private HTTPModulDTO getHttpModulDTO(int moduleId) {
        Optional<Modul> optModule = moduleService.getModul(moduleId);
        if (optModule.isPresent()) {
            return HTTPModulDTO.Converter.toDto(optModule.get());
        }
        return null;
    }
}
