/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 14:57:38
 * @modify date 2022-01-22 14:57:38
 * @desc [description]
 */
package de.hsos.swa.studiom.StudyGroupManagement.boundary.dto;

import de.hsos.swa.studiom.ModulManagment.entity.Modul;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;

public class PostGroupDTO {
    public int ownerMatNr;
    public String name;
    public int maxMember;
    public int moduleId;

    public PostGroupDTO() {
    }

    public PostGroupDTO(int matNr, String name, int maxMember, int moduleId) {
        this.ownerMatNr = matNr;
        this.name = name;
        this.maxMember = maxMember;
        this.moduleId = moduleId;
    }

    public static class Converter {
        public static PostGroupDTO toDTO(Group group) {
            return new PostGroupDTO(group.getOwner().getMatNr(), group.getName(), group.getMaxMembers(),
                    group.getModul().getModulID());
        }

        public static Group toGroup(PostGroupDTO gDTO, Student owner, Modul module) {
            return new Group(owner, module, gDTO.name, gDTO.maxMember);
        }
    }
}
