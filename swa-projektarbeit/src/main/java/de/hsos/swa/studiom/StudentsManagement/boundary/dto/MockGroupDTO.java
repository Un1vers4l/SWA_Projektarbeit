/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 15:02:26
 * @modify date 2022-01-22 15:02:26
 * @desc [description]
 */
package de.hsos.swa.studiom.StudentsManagement.boundary.dto;

import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;

public class MockGroupDTO {
    public String name;

    public MockGroupDTO(String name) {
        this.name = name;
    }

    public static class Converter {
        public static MockGroupDTO toDTO(Group group) {
            return new MockGroupDTO(group.getName());
        }

        public static Group toGroup(MockGroupDTO gDto) {
            Group mockGroup = new Group();
            mockGroup.setName(gDto.name);
            return mockGroup;
        }
    }
}
