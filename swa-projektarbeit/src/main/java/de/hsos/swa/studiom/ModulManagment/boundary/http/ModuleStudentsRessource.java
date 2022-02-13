package de.hsos.swa.studiom.ModulManagment.boundary.http;

import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.jwt.JsonWebToken;

import de.hsos.swa.studiom.ModulManagment.boundary.dto.modul.ModulDto;
import de.hsos.swa.studiom.ModulManagment.control.AnswerService;
import de.hsos.swa.studiom.ModulManagment.control.ModulService;
import de.hsos.swa.studiom.ModulManagment.control.QuestionService;
import de.hsos.swa.studiom.ModulManagment.entity.Modul;
import de.hsos.swa.studiom.StudentsManagement.boundary.dto.Student.StudentDTO;
import de.hsos.swa.studiom.StudentsManagement.control.StudentService;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;
import io.quarkus.qute.Template;

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
        boolean inModule = false;
        try {
            int matNr = Integer.valueOf(jwt.getClaim("matNr").toString());
            student = StudentDTO.Converter.toHTTPStudentDTO(studService.getStudent(matNr).get());
            inModule = moduleService.isInModule(matNr, moduleId);
        } catch (EntityNotFoundException e) {
            student = null;
            error = e.getMessage();
        }
        ModulDto moduleDetail = getHttpModulDTO(moduleId);
        return Response
                .ok(modulesStudents.data("student", student).data("moduleDetail", moduleDetail).data("groups", null)
                        .data("error", error).data("inModule", inModule)
                        .render())
                .build();
    }

    private ModulDto getHttpModulDTO(int moduleId) {
        Optional<Modul> optModule = moduleService.getModul(moduleId);
        if (optModule.isPresent()) {
            return ModulDto.Converter.toDetailHTTPModule(optModule.get());
        }
        return null;
    }
}
