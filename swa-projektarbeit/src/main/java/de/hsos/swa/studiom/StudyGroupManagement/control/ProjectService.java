/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-31 09:27:52
 * @modify date 2022-01-31 09:27:52
 * @desc [description]
 */
package de.hsos.swa.studiom.StudyGroupManagement.control;

import java.util.List;
import java.util.Optional;

import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;

public interface ProjectService {
    public Optional<Group> createProject(int matNr, int moduleId);

    public boolean deleteProject(int matNr, int groupId);

    public Optional<Group> getProject(int projectId);

    public Optional<List<Group>> getAllProjects();

    public Optional<Group> addStudent(int matNr, int groupId);
}
