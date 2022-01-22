/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 14:57:38
 * @modify date 2022-01-22 14:57:38
 * @desc [description]
 */
package de.hsos.swa.studiom.StudyGroupManagement.boundary.dto;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;
import de.hsos.swa.studiom.shared.mock.MockModule;

public class GroupDTO {
    public int ownerMatNr;
    public String name;
    public int maxMember;
    public int moduleId;

    public GroupDTO() {
    }

    public GroupDTO(int matNr, String name, int maxMember, int moduleId) {
        this.ownerMatNr = matNr;
        this.name = name;
        this.maxMember = maxMember;
        this.moduleId = moduleId;
    }

    public static class Converter {
        public static GroupDTO toDTO(Group group) {
            return new GroupDTO(group.getOwner().getMatNr(), group.getName(), group.getMaxMembers(),
                    group.getModule().getId());
        }

        public static Group toGroup(GroupDTO gDTO, Student owner, MockModule module) {
            return new Group(owner, module, gDTO.name, gDTO.maxMember);
        }
    }
}
