/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 14:37:40
 * @modify date 2022-02-01 11:40:35
 * @desc [description]
 */
package de.hsos.swa.studiom.StudyGroupManagement.gateway;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudyGroupManagement.control.GroupService;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;
import de.hsos.swa.studiom.StudyGroupManagement.entity.GroupType;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;
import de.hsos.swa.studiom.shared.exceptions.GroupManagementException;
import de.hsos.swa.studiom.shared.exceptions.JoinGroupException;
import de.hsos.swa.studiom.ModuleManagment.entity.Module;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.TransactionRequiredException;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

@ApplicationScoped
@Transactional
public class GroupRepository implements GroupService {

    private final GroupType TYPE = GroupType.STUDYGROUP;
    private final String FULL = "There is no free Space available in this group";
    private final String DUPLICATE = "Student is already a member of the group";

    Logger log = Logger.getLogger(GroupRepository.class);

    @Inject
    EntityManager em;

    @Override
    public Optional<Group> createGroup(int matNr, String name, int maxMember, int moduleId)
            throws EntityNotFoundException {
        try {
            Student owner = em.find(Student.class, matNr);
            Module module = em.find(Module.class, moduleId);
            if (module == null || owner == null) {
                if (module == null) {
                    log.error("Modul konnte nicht gefunden werden");
                    throw new EntityNotFoundException(Module.class, moduleId);
                } else {
                    log.error("Student konnte nicht gefunden werden");
                    throw new EntityNotFoundException(Student.class, matNr);
                }
            }
            Group group = new Group(owner, module, name, maxMember, TYPE);
            // group.addMember(owner);
            owner.addGroup(group);
            em.persist(owner);
            em.persist(group);
            return Optional.ofNullable(group);
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            log.error("Eine Exception wurde geworfen \n" + e.toString());
            return Optional.ofNullable(null);
        }
    }

    @Override
    public Optional<Group> changeGroup(int matNr, int groupId, Group newGroup)
            throws EntityNotFoundException, GroupManagementException {
        try {
            Group group = em.find(Group.class, groupId);
            if (group == null) {
                log.error("Gruppe konnte nicht gefunden werden");
                throw new EntityNotFoundException(Group.class, groupId);
            }
            if (group.getOwner().getMatNr() != matNr) {
                log.error("Nur der Ersteller der Gruppe darf diese auch verändern");
                throw new GroupManagementException(TYPE);
            }
            group.setMaxMembers(newGroup.getMaxMembers());
            group.setName(newGroup.getName());
            em.persist(group);
            return Optional.ofNullable(group);
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            log.error("Eine Exception wurde geworfen \n" + e.toString());
            return Optional.ofNullable(null);
        }
    }

    @Override
    public boolean deleteGroup(int matNr, int groupId) throws EntityNotFoundException, GroupManagementException {
        try {
            Group group = em.find(Group.class, groupId);
            if (group == null) {
                log.error("Gruppe konnte nicht gefunden werden");
                throw new EntityNotFoundException(Group.class, groupId);
            }
            if (group.getOwner().getMatNr() != matNr) {
                log.error("Nur der Ersteller der Gruppe darf diese auch löschen");
                throw new GroupManagementException(TYPE);
            }
            for (Student student : group.getMember()) {
                student.removeGroup(group);
            }
            group.setOwner(null);
            em.remove(group);
            return true;
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            log.error("Eine Exception wurde geworfen \n" + e.toString());
            return false;
        }
    }

    @Override
    public Optional<Group> getGroup(int groupId) throws EntityNotFoundException {
        try {
            Group group = em.find(Group.class, groupId);
            if (group == null) {
                log.error("Gruppe konnte nicht gefunden werden");
                throw new EntityNotFoundException(Group.class, groupId);
            }

            if (group.getType() != TYPE) {
                log.error("Dies ist ein projekt und keine Gruppe");
                // TODO: Exception: Keine Gruppe vom Typ Projekt
                return Optional.ofNullable(null);
            }
            return Optional.ofNullable(group);

        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            log.error("Eine Exception wurde geworfen \n" + e.toString());
            return Optional.ofNullable(null);
        }
    }

    @Override
    public Optional<List<Group>> getAllGroup() {
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
    public Optional<Group> addStudent(int groupId, int matNr) throws JoinGroupException, EntityNotFoundException {
        try {
            Student student = em.find(Student.class, matNr);
            Group group = em.find(Group.class, groupId);
            if (student == null) {
                log.error("Student konnte nicht gefunden werden");
                throw new EntityNotFoundException(Student.class, matNr);
            }
            if (group == null) {
                log.error("Gruppe konnte nicht gefunden werden");
                throw new EntityNotFoundException(Group.class, groupId);
            }
            if (group.getMaxMembers() <= group.getMember().size()) {
                log.error(FULL);
                throw new JoinGroupException(TYPE, groupId, matNr, FULL);
            }
            for (Student stud : group.getMember()) {
                if (stud.getMatNr() == student.getMatNr()) {
                    log.error("Ein Student kann nicht mehrmals einer Gruppe beitreten");
                    throw new JoinGroupException(TYPE, groupId, matNr, DUPLICATE);
                }
            }
            group.addMember(student);
            em.persist(group);
            em.flush();
            student.addGroup(group);
            em.persist(student);
            return Optional.ofNullable(group);
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            log.error("Eine Exception wurde geworfen \n" + e.toString());
            return Optional.ofNullable(null);
        }
    }

    @Override
    public Optional<Group> removeStudent(int groupId, int matNr) throws EntityNotFoundException {
        try {
            Student student = em.find(Student.class, matNr);
            if (student == null) {
                log.error("Student konnte nicht gefunden werden");
                throw new EntityNotFoundException(Student.class, matNr);
            }
            Group group = em.find(Group.class, groupId);
            if (group == null) {
                log.error("Gruppe konnte nicht gefunden werden");
                throw new EntityNotFoundException(Group.class, groupId);
            }
            if (group.getMember().size() <= 1) {
                log.error("Ersteller der Gruppe kann nicht austreten");
                // TODO: Exception: Gruppe hat nur den Owner als Mitglied
                return Optional.ofNullable(null);
            }
            if (student.getMatNr() == group.getOwner().getMatNr()) {
                log.error("Ersteller der Gruppe kann nicht austreten");
                // TODO: Exception: Owner kann nicht entfernt werden
                return Optional.ofNullable(null);
            }
            if (!group.removeMember(student)) {
                log.error("Fehler beim Entfernen des Studenten");
                return Optional.ofNullable(null);
            }
            if (!student.removeGroup(group)) {
                log.error("Fehler beim Entfernen des Studenten");
                return Optional.ofNullable(null);
            }
            em.persist(group);
            em.flush();
            em.persist(student);
            return Optional.ofNullable(group);
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            // TODO: Exception
            log.error("Eine Exception wurde geworfen \n" + e.toString());
            return Optional.ofNullable(null);

        }
    }

}
