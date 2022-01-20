package de.hsos.swa.studiom.StudentsManagement.boundary.dto;

import de.hsos.swa.studiom.shared.mock.MockGroup;

public class GroupDTO {
    public String name;

    public GroupDTO(String name) {
        this.name = name;
    }

    public static class Converter {
        public static GroupDTO toDTO(MockGroup group) {
            return new GroupDTO(group.getName());
        }

        public static MockGroup toModule(GroupDTO gDto) {
            MockGroup mockGroup = new MockGroup();
            mockGroup.setName(gDto.name);
            return mockGroup;
        }
    }
}
