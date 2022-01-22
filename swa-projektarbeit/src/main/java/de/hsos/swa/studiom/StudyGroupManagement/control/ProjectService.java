package de.hsos.swa.studiom.StudyGroupManagement.control;

import java.util.List;
import java.util.Optional;

import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;
/**
 * @author Joana Wegener
 */
public interface ProjectService {
    public Optional<Group> createProject(int matNr, int moduleId, String name);

    public Optional<Group> changeProject(int matNr, int groupId, Group newGroup);

    public boolean deleteProject(int matNr, int groupId);

    public Optional<Group> getProject(int projectId);

    public Optional<List<Group>> getAllProjects();
}
