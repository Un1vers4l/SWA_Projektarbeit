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
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;
import de.hsos.swa.studiom.shared.exceptions.GroupManagementException;
import de.hsos.swa.studiom.shared.exceptions.JoinGroupException;

public interface ProjectService {
    public Optional<Group> createProject(int matNr, int moduleId) throws EntityNotFoundException, JoinGroupException;

    public boolean deleteProject(int matNr, int groupId) throws EntityNotFoundException, GroupManagementException;

    public Optional<Group> getProject(int projectId) throws EntityNotFoundException;

    public Optional<List<Group>> getAllProjects();

    public Optional<Group> addStudent(int matNr, int groupId) throws EntityNotFoundException, JoinGroupException;
}
