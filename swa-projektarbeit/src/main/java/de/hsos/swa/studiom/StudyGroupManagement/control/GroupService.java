package de.hsos.swa.studiom.StudyGroupManagement.control;

import java.util.List;
import java.util.Optional;

import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;

/**
 * @author Joana Wegener
 */

public interface GroupService {
    public Optional<Group> createGroup(int matNr, String name, int maxMember, int moduleId);

    public Optional<Group> changeGroup(int matNr, Group newGroup);

    public boolean deleteGroup(int matNr, int groupId);

    public Optional<Group> getGroup(int groupId);

    public Optional<List<Group>> getAllGroup();
}