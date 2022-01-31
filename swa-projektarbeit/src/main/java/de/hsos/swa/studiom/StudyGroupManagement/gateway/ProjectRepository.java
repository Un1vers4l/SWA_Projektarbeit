/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-31 09:28:10
 * @modify date 2022-01-31 13:36:16
 * @desc [description]
 */
package de.hsos.swa.studiom.StudyGroupManagement.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.TransactionRequiredException;
import javax.transaction.Transactional;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudyGroupManagement.control.ProjectService;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;
import de.hsos.swa.studiom.StudyGroupManagement.entity.GroupType;
import de.hsos.swa.studiom.shared.mock.MockModule;

@Transactional
@ApplicationScoped
public class ProjectRepository implements ProjectService {

    @Inject
    EntityManager em;

    private final GroupType TYPE = GroupType.PROJECT;

    @Override
    public Optional<Group> createProject(int matNr, int moduleId) {
        try {
            Student owner = em.find(Student.class, matNr);
            MockModule module = em.find(MockModule.class, moduleId);
            if (owner == null || module == null) {
                // TODO: Exception: Owner oder Modul nicht gefunden
                return Optional.ofNullable(null);
            }
            if (module.isProject() == false) {
                // TODO: Exception: Modul bietet kein Projekt an
                return Optional.ofNullable(null);
            }
            if (!isInProject(matNr, moduleId)) {
                // TODO: Exception: Student ist schon in einem Projekt eingetragen
                return Optional.ofNullable(null);
            }
            Group projectgroup = new Group(owner, module, module.getName() + " Projektarbeit_" + owner.getMatNr(), 2,
                    TYPE);
            projectgroup.addMember(owner);
            owner.addGroup(projectgroup);
            em.persist(projectgroup);
            return Optional.ofNullable(projectgroup);
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            // TODO: Exception
            return Optional.ofNullable(null);
        }
    }

    @Override
    public boolean deleteProject(int matNr, int groupId) {
        try {
            Group group = em.find(Group.class, groupId);
            Student student = em.find(Student.class, matNr);

            if (group == null || student == null) {
                // TODO: Exception: Gruppe oder Student nicht gefunden
                return false;
            }

            if (group.getOwner().getMatNr() != matNr) {
                // TODO: Exception: Student ist nicht berechtigt
                return false;
            }

            if (group.getMaxMembers() == group.getMember().size()) {
                // TODO: Exception: Volles Projekt kann nicht gelöscht werden
                return false;
            }

            for (Student stud : group.getMember()) {
                stud.removeGroup(group);
            }
            group.setOwner(null);
            em.remove(group);
            return true;
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            // TODO: Exception
            return false;
        }
    }

    @Override
    public Optional<Group> getProject(int projectId) {
        try {
            Group project = em.find(Group.class, projectId);
            if (project == null) {
                // TODO: Exception: Projekt nich gefunden
                return Optional.ofNullable(null);
            }
            if (project.getType() != TYPE) {
                // TODO: Exception: Projekt nicht gefunden
                return Optional.ofNullable(null);
            }
            return Optional.ofNullable(project);
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            // TODO: Exception
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
                    System.out.println("type: " + TYPE.ordinal());
                    returnList.add(group);
                }
            }
            return Optional.ofNullable(returnList);
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            // TODO: Exception
            return Optional.ofNullable(null);
        }
    }

    @Override
    public Optional<Group> addStudent(int matNr, int groupId) {
        try {
            Student student = em.find(Student.class, matNr);
            Group project = em.find(Group.class, groupId);
            if (project == null || student == null) {
                // TODO: Exception: Projekt oder Student nicht gefunden
                return Optional.ofNullable(null);
            }
            if (project.getMaxMembers() == project.getMember().size()) {
                // TODO: Exception: Projekt ist voll
                return Optional.ofNullable(null);
            }
            if (!isInProject(matNr, project.getModule().getId())) {
                // TODO: Exception: Student ist bereits in anderem Modul eingetragen
                return Optional.ofNullable(null);
            }

            project.addMember(student);
            student.addGroup(project);
            em.persist(student);
            em.persist(project);
            return Optional.ofNullable(project);
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            // TODO: Exception
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
}
