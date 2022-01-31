/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 14:37:40
 * @modify date 2022-01-31 11:33:04
 * @desc [description]
 */
package de.hsos.swa.studiom.StudyGroupManagement.gateway;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudyGroupManagement.control.GroupService;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;
import de.hsos.swa.studiom.StudyGroupManagement.entity.GroupType;
import de.hsos.swa.studiom.shared.mock.MockModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.TransactionRequiredException;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class GroupRepository implements GroupService {

    @Inject
    EntityManager em;

    private final GroupType TYPE = GroupType.STUDYGROUP;

    @Override
    public Optional<Group> createGroup(int matNr, String name, int maxMember, int moduleId) {
        try {
            Student owner = em.find(Student.class, matNr);
            MockModule module = em.find(MockModule.class, moduleId);
            if (module == null || owner == null) {
                if (module == null) {
                    // //TODO: Exception: Modul nicht gefunden
                    System.out.println("module null" + moduleId);
                } else {
                    // //TODO: Exception: Student nicht gefunden
                    System.out.println("Student null");
                }
                return Optional.ofNullable(null);
            }
            Group group = new Group(owner, module, name, maxMember, TYPE);
            // group.addMember(owner);
            owner.addGroup(group);
            em.persist(owner);
            em.persist(group);
            return Optional.ofNullable(group);
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            // TODO: Exception
            return Optional.ofNullable(null);
        }
    }

    @Override
    public Optional<Group> changeGroup(int matNr, int groupId, Group newGroup) {
        try {
            Group group = em.find(Group.class, groupId);
            if (group == null) {
                // TODO: Exception: Gruppe nicht gefunden
                return Optional.ofNullable(null);
            }
            if (group.getOwner().getMatNr() != matNr) {
                // TODO: Exception: Nicht berechtigt
                return Optional.ofNullable(null);
            }
            group.setMaxMembers(newGroup.getMaxMembers());
            group.setName(newGroup.getName());
            em.persist(group);
            return Optional.ofNullable(group);
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            // TODO: Exception
            return Optional.ofNullable(null);
        }
    }

    @Override
    public boolean deleteGroup(int matNr, int groupId) {
        try {
            Group group = em.find(Group.class, groupId);
            if (group == null) {
                // TODO: Exception: Gruppe nicht gefunden
                return false;
            }
            if (group.getOwner().getMatNr() != matNr) {
                // TODO: Exception: Nicht berechtigt
                return false;
            }
            for (Student student : group.getMember()) {
                student.removeGroup(group);
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
    public Optional<Group> getGroup(int groupId) {
        try {
            Group group = em.find(Group.class, groupId);
            if (group == null) {
                // TODO: Exception: Gruppe nicht gefunden
                return Optional.ofNullable(null);
            }

            if (group.getType() != TYPE) {
                // TODO: Exception: Keine Gruppe vom Typ Projekt
                return Optional.ofNullable(null);
            }
            return Optional.ofNullable(group);

        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            // TODO: Exception
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
            // TODO: Exception
            return Optional.ofNullable(null);
        }
    }

    @Override
    public Optional<Group> addStudent(int groupId, int matNr) {
        try {
            Student student = em.find(Student.class, matNr);
            if (student != null) {
                Group group = em.find(Group.class, groupId);
                if (group != null && group.getMaxMembers() > group.getMember().size()) {
                    for (Student stud : group.getMember()) {
                        if (stud.getMatNr() == student.getMatNr()) {

                            return Optional.ofNullable(null);
                        }
                    }
                    group.addMember(student);
                    em.persist(group);
                    em.flush();
                    student.addGroup(group);
                    em.persist(student);
                    return Optional.ofNullable(group);
                }
            }
            return Optional.ofNullable(null);
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            return Optional.ofNullable(null);
            // TODO: handle exception
        }
    }

    @Override
    public Optional<Group> removeStudent(int groupId, int matNr) {
        try {
            Student student = em.find(Student.class, matNr);
            if (student != null) {
                Group group = em.find(Group.class, groupId);
                if (group != null && group.getMember().size() > 1) {
                    if (student.getMatNr() == group.getOwner().getMatNr()) {
                        return Optional.ofNullable(null);
                    }
                    if (!group.removeMember(student)) {
                        return Optional.ofNullable(null);
                    }
                    if (!student.removeGroup(group)) {
                        return Optional.ofNullable(null);
                    }
                    em.persist(group);
                    em.flush();
                    em.persist(student);
                    return Optional.ofNullable(group);
                }
            }
            return Optional.ofNullable(null);
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            return Optional.ofNullable(null);
            // TODO: handle exception
        }
    }

}
