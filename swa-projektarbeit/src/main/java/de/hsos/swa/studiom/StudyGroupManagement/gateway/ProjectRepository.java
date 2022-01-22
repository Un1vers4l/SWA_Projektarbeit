package de.hsos.swa.studiom.StudyGroupManagement.gateway;

import java.util.List;
import java.util.Optional;

import de.hsos.swa.studiom.StudyGroupManagement.control.ProjectService;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;
/**
 * @author Joana Wegener
 */
public class ProjectRepository implements ProjectService {

    @Override
    public Optional<Group> createProject(int matNr, int moduleId, String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Group> changeProject(int matNr, int groupId, Group newGroup) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean deleteProject(int matNr, int groupId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Optional<Group> getProject(int projectId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<List<Group>> getAllProjects() {
        // TODO Auto-generated method stub
        return null;
    }

}
