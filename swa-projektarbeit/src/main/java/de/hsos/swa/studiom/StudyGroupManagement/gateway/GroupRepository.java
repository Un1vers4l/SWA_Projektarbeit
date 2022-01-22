/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 14:37:40
 * @modify date 2022-01-22 14:37:40
 * @desc [description]
 */
package de.hsos.swa.studiom.StudyGroupManagement.gateway;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudentsManagement.gateway.StudentRepository;
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
import javax.ws.rs.Path;

@ApplicationScoped
@Transactional
@Path("/api/v1/groups")
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
                    // TODO: Exception werfen
                    System.out.println("module null" + moduleId);
                } else {
                    // TODO: Exception werfen
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
            return Optional.ofNullable(null);
        }
    }

    @Override
    public Optional<Group> changeGroup(int matNr, int groupId, Group newGroup) {
        try {
            Group group = em.find(Group.class, groupId);
            if (group == null) {
                // TODO: Exception werfen
                return Optional.ofNullable(null);
            }
            if (group.getOwner().getMatNr() != matNr) {
                // TODO: Exception werfen
                return Optional.ofNullable(null);
            }
            group.setMaxMembers(newGroup.getMaxMembers());
            group.setName(newGroup.getName());
            em.persist(group);
            return Optional.ofNullable(group);
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            return Optional.ofNullable(null);
        }
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
        try {
            return Optional.ofNullable(em.createNamedQuery("Groups.findAll", Group.class).getResultList());

        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            return Optional.ofNullable(null);
            // TODO: handle exception
        }
    }

}
