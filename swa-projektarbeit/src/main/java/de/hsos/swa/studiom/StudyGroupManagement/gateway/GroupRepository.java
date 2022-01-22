/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 14:37:40
 * @modify date 2022-01-22 14:37:40
 * @desc [description]
 */
package de.hsos.swa.studiom.StudyGroupManagement.gateway;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudyGroupManagement.control.GroupService;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;
import de.hsos.swa.studiom.StudyGroupManagement.entity.GroupType;
import de.hsos.swa.studiom.shared.mock.MockModule;

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
            Student student = em.find(Student.class, matNr);
            moduleId = testAddModule();
            MockModule module = em.find(MockModule.class, moduleId);
            if (module == null || student == null) {
                if (module == null) {
                    System.out.println("module null" + moduleId);
                } else {
                    System.out.println("Student null");
                }
                return Optional.ofNullable(null);
            }
            Group group = new Group(student, module, name, maxMember, TYPE);
            em.persist(group);
            return Optional.ofNullable(group);
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            return Optional.ofNullable(null);
        }
    }

    public int testAddModule() {
        try {
            MockModule module = new MockModule();
            module.setName("Test Modul");
            em.persist(module);
            return module.getId();
        } catch (Exception e) {
        }
        return -1;
    }

    @Override
    public Optional<Group> changeGroup(int matNr, Group newGroup) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean deleteGroup(int matNr, int groupId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Optional<Group> getGroup(int groupId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<List<Group>> getAllGroup() {
        // TODO Auto-generated method stub
        return null;
    }

}
