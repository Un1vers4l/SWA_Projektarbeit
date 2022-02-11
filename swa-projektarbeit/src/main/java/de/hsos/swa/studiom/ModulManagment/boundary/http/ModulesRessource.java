/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-02-07 13:55:39
 * @modify date 2022-02-07 13:55:39
 * @desc [description]
 */
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
import org.eclipse.microprofile.jwt.JsonWebToken;

import de.hsos.swa.studiom.ModulManagment.control.AnswerService;
import de.hsos.swa.studiom.ModulManagment.control.ModulService;
import de.hsos.swa.studiom.ModulManagment.control.QuestionService;
import de.hsos.swa.studiom.StudentsManagement.boundary.dto.Student.HTTPStudentDTO;
import de.hsos.swa.studiom.StudentsManagement.control.StudentService;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;
import io.quarkus.qute.Template;

@Transactional
@RequestScoped
@Path("modules")
@Produces(MediaType.TEXT_HTML)
@Consumes(MediaType.TEXT_HTML)
@RolesAllowed("STUDENT")
public class ModulesRessource {

    @Inject
    Template modulesForum;

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
    public Response getModules() {
        String error = "error";
        HTTPStudentDTO student;
        try {
            student = getHttpStudentDTO();
        } catch (EntityNotFoundException e) {
            student = null;
            error = e.getMessage();
        }
        return Response
                .ok(modulesForum.data("student", student).data("moduleDetail", null).data("inModule", true)
                        .data("error", error)
                        .render())
                .build();
    }

    private HTTPStudentDTO getHttpStudentDTO() throws EntityNotFoundException {
        Object claim = jwt.getClaim("matNr");
        if (claim == null) {
            return null;
        }
        int matNr = Integer.valueOf(claim.toString());
        Optional<Student> opt;
        opt = studService.getStudent(matNr);
        if (opt.isPresent()) {
            return HTTPStudentDTO.Converter.toHTTPStudentDTO(opt.get());
        }
        return null;
    }
}
