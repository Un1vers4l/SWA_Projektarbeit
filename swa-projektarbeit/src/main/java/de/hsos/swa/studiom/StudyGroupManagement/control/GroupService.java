/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 19:50:02
 * @modify date 2022-01-22 19:50:02
 * @desc [description]
 */
package de.hsos.swa.studiom.StudyGroupManagement.control;

import java.util.List;
import java.util.Optional;

import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;

public interface GroupService {
    public Optional<Group> createGroup(int matNr, String name, int maxMember, int moduleId);

    public Optional<Group> changeGroup(int matNr, int groupId, Group newGroup);

    public boolean deleteGroup(int matNr, int groupId);

    public Optional<Group> getGroup(int groupId);

    public Optional<List<Group>> getAllGroup();
}