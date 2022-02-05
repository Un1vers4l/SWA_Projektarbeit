/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-31 09:28:10
 * @modify date 2022-02-01 11:41:12
 * @desc [description]
 */
package de.hsos.swa.studiom.StudyGroupManagement.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.TransactionRequiredException;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import de.hsos.swa.studiom.ModulManagment.entity.Modul;
import de.hsos.swa.studiom.ModulManagment.gateway.ModulRepository;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudentsManagement.gateway.StudentRepository;
import de.hsos.swa.studiom.StudyGroupManagement.control.ProjectService;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;
import de.hsos.swa.studiom.StudyGroupManagement.entity.GroupType;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;
import de.hsos.swa.studiom.shared.exceptions.GroupManagementException;
import de.hsos.swa.studiom.shared.exceptions.JoinGroupException;

@Transactional
@RequestScoped
public class ProjectRepository implements ProjectService {

    private final GroupType TYPE = GroupType.PROJECT;
    private final String FULL = "There is no free Space available in this project";
    private final String DUPLICATE = "Student is already a member of a project in this module";
    private final String NOPROJECT = "Module does not offer a project";
    Logger log = Logger.getLogger(ProjectRepository.class);

    @Inject
    EntityManager em;

    @Inject
    ModulRepository modRepos;

    @Inject
    StudentRepository studRepos;

    @Override
    public Optional<Group> createProject(int matNr, int moduleId) throws EntityNotFoundException, JoinGroupException {
        try {
            Student owner = studRepos.getStudent(matNr).get();
            Modul module = modRepos.getModul(moduleId).get();
            if (module == null || owner == null) {
                return Optional.ofNullable(null);
            }

            if (module.isProject() == false) {
                log.error("Dieses Modul bietet kein Projekt an");
                throw new JoinGroupException(TYPE, matNr, NOPROJECT);
            }

            if (!isInProject(matNr, moduleId)) {
                log.error(
                        "Der Student ist bereits in einem Projekt eingetragen und kann nicht einem weiteren beitreten");
                throw new JoinGroupException(TYPE, matNr, DUPLICATE);
            }
            Group projectgroup = new Group(owner, module, module.getName() + " Projektarbeit_" + owner.getMatNr(), 2,
                    TYPE);
            projectgroup.addMember(owner);
            owner.addGroup(projectgroup);
            em.persist(projectgroup);
            return Optional.ofNullable(projectgroup);
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            log.error("Eine Exception wurde geworfen \n" + e.toString());
            return Optional.ofNullable(null);
        }
    }

    @Override
    public boolean deleteProject(int matNr, int projectId) throws EntityNotFoundException, GroupManagementException {
        try {
            Group project = getProject(projectId).get();
            Student student = studRepos.getStudent(matNr).get();

            if (project == null || student == null) {
                return false;
            }

            if (project.getOwner().getMatNr() != matNr) {
                log.error("Das Projekt kann nur von dem Ersteller des Projektes gelöscht werden");
                throw new GroupManagementException(TYPE);
            }

            if (project.getMaxMembers() == project.getMember().size()) {
                log.error("Ein volles Projekt kann nicht gelöscht werden");
                // TODO: Exception: Volles Projekt kann nicht gelöscht werden
                return false;
            }

            for (Student stud : project.getMember()) {
                stud.removeGroup(project);
            }
            project.setOwner(null);
            em.remove(project);
            return true;
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            log.error("Eine Exception wurde geworfen \n" + e.toString());
            return false;
        }
    }

    @Override
    public Optional<Group> getProject(int projectId) throws EntityNotFoundException {
        try {
            Group project = em.find(Group.class, projectId);
            if (project == null) {
                log.error("Es wurde keine Gruppe oder Projekt gefunden");
                throw new EntityNotFoundException(Group.class, projectId);
            }
            if (project.getType() != TYPE) {
                log.error("Dies ist eine Gruppe und kein Projekt");
                // TODO: Exception: Projekt nicht gefunden
                return Optional.ofNullable(null);
            }
            return Optional.ofNullable(project);
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            log.error("Eine Exception wurde geworfen \n" + e.toString());
            return Optional.ofNullable(null);
        }
    }

    @Override
    public Optional<List<Group>> getAllProjects() {
        try {
            List<Group> resultList = em.createNamedQuery("Groups.findAll", Group.class).getResultList();
            List<Group> returnList = new ArrayList<>();
            for (Group group : resultList) {
                if (group.getType() == TYPE) {
                    returnList.add(group);
                }
            }
            return Optional.ofNullable(returnList);
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            log.error("Eine Exception wurde geworfen \n" + e.toString());
            return Optional.ofNullable(null);
        }
    }

    @Override
    public Optional<Group> addStudent(int matNr, int projectId) throws EntityNotFoundException, JoinGroupException {
        try {
            Group project = getProject(projectId).get();
            Student student = studRepos.getStudent(matNr).get();
            if (project == null || student == null) {
                return Optional.ofNullable(null);
            }
            if (project.getMaxMembers() == project.getMember().size()) {
                log.error("Das Projekt ist bereits voll");
                throw new JoinGroupException(TYPE, projectId, matNr, FULL);
            }
            if (!isInProject(matNr, project.getModul().getModulID())) {
                log.error("Student ist bereits in einem anderen Projekt in diesem Modul eingetragen");
                throw new JoinGroupException(TYPE, projectId, matNr, DUPLICATE);
            }
            project.addMember(student);
            student.addGroup(project);
            em.persist(student);
            em.persist(project);
            return Optional.ofNullable(project);
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            log.error("Eine Exception wurde geworfen \n" + e.toString());
            return Optional.ofNullable(null);
        }
    }

    private boolean isInProject(int matNr, int moduleId) {
        List<Group> resultList = em
                .createQuery("SELECT g FROM Group g WHERE g.module.id= :moduleId AND g.type = :type",
                        Group.class)
                .setParameter("moduleId", moduleId).setParameter("type", TYPE).getResultList();
        for (Group group : resultList) {
            for (Student student : group.getMember()) {
                if (student.getMatNr() == matNr) {
                    // Student ist bereits in Projekt
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Optional<List<Group>> getProjectsForStudent(int matNr) {
        try {
            List<Group> resultList = em
                    .createQuery("SELECT p FROM Group p WHERE p.type = :type AND p.owner.matNr = :matNr", Group.class)
                    .setParameter("type", TYPE).setParameter("matNr", matNr).getResultList();
            return Optional.ofNullable(resultList);
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            log.error("Eine Exception wurde geworfen \n" + e.toString());
            return Optional.ofNullable(null);
        }
    }
}
