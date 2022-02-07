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
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.jwt.JsonWebToken;

import de.hsos.swa.studiom.ModulManagment.control.ModulService;
import de.hsos.swa.studiom.ModulManagment.entity.Modul;
import de.hsos.swa.studiom.StudentsManagement.boundary.dto.HTTPStudentDTO;
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
    Template modules;

    @Inject
    ModulService moduleService;

    @Inject
    StudentService studService;

    @Inject
    JsonWebToken jwt;

    @GET
    public Response getModules() {
        Object claim = jwt.getClaim("matNr");
        if (claim == null) {
            return Response.status(Status.UNAUTHORIZED).build();
        }
        int matNr = Integer.valueOf(claim.toString());
        Optional<Student> opt;
        try {
            opt = studService.getStudent(matNr);
            if (!opt.isPresent()) {
                return Response.status(Status.INTERNAL_SERVER_ERROR).build();
            }
            HTTPStudentDTO student = HTTPStudentDTO.Converter.toHTTPStudentDTO(opt.get());
            return Response
                    .ok(modules.data("student", student).data("moduleDetail", null).data("inModule", false).render())
                    .build();
        } catch (EntityNotFoundException e) {
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{moduleId}")
    public Response getDetailModule(@PathParam("moduleId") int moduleId) {
        Object claim = jwt.getClaim("matNr");
        if (claim == null) {
            return Response.status(Status.UNAUTHORIZED).build();
        }
        int matNr = Integer.valueOf(claim.toString());
        Optional<Student> opt;
        try {
            opt = studService.getStudent(matNr);
            if (!opt.isPresent()) {
                return Response.status(Status.INTERNAL_SERVER_ERROR).build();
            }
            HTTPStudentDTO student = HTTPStudentDTO.Converter.toHTTPStudentDTO(opt.get());
            Optional<Modul> optModule = moduleService.getModul(moduleId);
            if (optModule.isPresent()) {
                return Response
                        .ok(modules.data("student", student).data("moduleDetail", null).data("inModule", false)
                                .render())
                        .build();
            }
            return Response
                    .ok(modules.data("student", student).data("moduleDetail", null).data("inModule", false).render())
                    .build();
        } catch (EntityNotFoundException e) {
            return Response.status(Status.BAD_REQUEST).build();
        }
    }
}
