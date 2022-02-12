/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 20:11:37
 * @modify date 2022-01-22 20:11:37
 * @desc [description]
 */
package de.hsos.swa.studiom.StudyGroupManagement.boundary.dto;

import java.util.ArrayList;
import java.util.List;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;

public class GroupDTO {
    public int id;
    public StudentDTO owner;
    public String name;
    public int maxMember;
    public int moduleId;
    public List<StudentDTO> members = new ArrayList<>();

    public GroupDTO() {
    }

    public static class Converter {
        public static GroupDTO toDTO(Group group) {
            GroupDTO gDTO = new GroupDTO();
            gDTO.id = group.getGroupId();
            gDTO.owner = StudentDTO.Converter.toDTO(group.getOwner());
            gDTO.name = group.getName();
            gDTO.maxMember = group.getMaxMembers();
            gDTO.moduleId = group.getModul().getModulID();
            for (Student member : group.getMember()) {
                gDTO.members.add(StudentDTO.Converter.toDTO(member));
            }
            return gDTO;
        }

        
    }

}
