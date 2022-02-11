/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 15:02:26
 * @modify date 2022-01-22 15:02:26
 * @desc [description]
 */
package de.hsos.swa.studiom.StudentsManagement.boundary.dto.Group;

import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;

public class GroupDTO {
    public int id;
    public String name;

    public GroupDTO(String name) {
        this.name = name;
    }

    public static class Converter {
        public static GroupDTO toDTO(Group group) {
            GroupDTO mockGroupDTO = new GroupDTO(group.getName());
            mockGroupDTO.id = group.getGroupId();
            return mockGroupDTO;
        }

        public static Group toGroup(GroupDTO gDto) {
            Group mockGroup = new Group();
            mockGroup.setName(gDto.name);
            return mockGroup;
        }
    }
}
