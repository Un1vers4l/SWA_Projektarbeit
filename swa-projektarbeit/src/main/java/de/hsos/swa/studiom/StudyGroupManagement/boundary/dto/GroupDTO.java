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
    public int ownerMatNr;
    public String name;
    public int maxMember;
    public int moduleId;
    public List<Integer> membersMatNr = new ArrayList<>();

    public GroupDTO() {
    }

    public static class Converter {
        public static GroupDTO toDTO(Group group) {
            GroupDTO gDTO = new GroupDTO();
            gDTO.ownerMatNr = group.getOwner().getMatNr();
            gDTO.name = group.getName();
            gDTO.maxMember = group.getMaxMembers();
            gDTO.moduleId = group.getModule().getId();
            for (Student member : group.getMember()) {
                gDTO.membersMatNr.add(member.getMatNr());
            }
            return gDTO;
        }
    }

}
