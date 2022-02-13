package de.hsos.swa.studiom.ModulManagment.boundary.http;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import de.hsos.swa.studiom.ModulManagment.entity.Modul;
import de.hsos.swa.studiom.StudentsManagement.boundary.dto.Student.StudentDTO;
import de.hsos.swa.studiom.StudentsManagement.control.StudentService;
import de.hsos.swa.studiom.StudyGroupManagement.boundary.dto.GroupDTO;
import de.hsos.swa.studiom.StudyGroupManagement.control.GroupService;
import de.hsos.swa.studiom.StudyGroupManagement.control.ProjectService;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;
import de.hsos.swa.studiom.StudyGroupManagement.entity.GroupType;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;
import de.hsos.swa.studiom.shared.exceptions.JoinGroupException;
import io.quarkus.qute.Template;

@Transactional
@RequestScoped
@Produces(MediaType.TEXT_HTML)
@Consumes(MediaType.TEXT_HTML)
@RolesAllowed("STUDENT")
@Path("modules/{moduleId}/groups")
public class ModuleGroupRessource {
    @Inject
    Template modulesGroups;

    @Inject
    Template modulesInfo;

    @Inject
    ModulService moduleService;

    @Inject
    StudentService studService;

    @Inject
    GroupService groupService;

    @Inject
    ProjectService projService;

    @Inject
    QuestionService questService;

    @Inject
    AnswerService answerService;

    @Inject
    JsonWebToken jwt;

    @GET
    public Response getModuleGroups(@PathParam("moduleId") int moduleId,
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
        Optional<List<Group>> groupByModule = groupService.getGroupByModule(moduleId);
        if (groupByModule.isPresent()) {
            List<GroupDTO> groups = groupByModule.get().stream().map(GroupDTO.Converter::toHTTPGroupDTO)
                    .collect(Collectors.toList());
            return Response
                    .ok(modulesGroups.data("student", student).data("moduleDetail", moduleDetail)
                            .data("groups", groups)
                            .data("error", error).data("inModule", inModule)
                            .render())
                    .build();
        }
        error = "No Groups found for this Module";
        return Response
                .ok(modulesGroups.data("student", student).data("moduleDetail", moduleDetail).data("groups", null)
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

    @POST
    @Path("/newGroup")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response newGroup(@PathParam("moduleId") int moduleId,
            @DefaultValue("STUDYGROUP") @FormParam("groupType") GroupType type,
            @DefaultValue("3") @FormParam("maxMember") int maxMember,
            @DefaultValue("Lerngruppe") @FormParam("groupName") String groupName) {
        int matNr = Integer.valueOf(jwt.getClaim("matNr").toString());
        String error = "error";
        if (type == GroupType.PROJECT) {
            try {
                projService.createProject(matNr, moduleId);
            } catch (EntityNotFoundException | JoinGroupException e) {
                error = e.getMessage();
            }
        } else {
            try {
                groupService.createGroup(matNr, groupName, maxMember, moduleId);
            } catch (EntityNotFoundException e) {
                error = e.getMessage();
            }
        }
        return Response
                .seeOther(UriBuilder.fromPath("/modules/" + moduleId + "/groups").queryParam("error", error).build())
                .build();
    }
}
