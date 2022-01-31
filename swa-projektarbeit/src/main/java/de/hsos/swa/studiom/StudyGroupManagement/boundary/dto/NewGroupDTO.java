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


public class NewGroupDTO {
    public int ownerMatNr;
    public String name;
    public int maxMember;
    public int moduleId;

    public NewGroupDTO() {
    }

    public NewGroupDTO(int matNr, String name, int maxMember, int moduleId) {
        this.ownerMatNr = matNr;
        this.name = name;
        this.maxMember = maxMember;
        this.moduleId = moduleId;
    }

    public static class Converter {
        public static NewGroupDTO toDTO(Group group) {
            return new NewGroupDTO(group.getOwner().getMatNr(), group.getName(), group.getMaxMembers(),
                    group.getModule().getId());
        }

        public static Group toGroup(NewGroupDTO gDTO, Student owner, MockModule module) {
            return new Group(owner, module, gDTO.name, gDTO.maxMember);
        }
    }
}
