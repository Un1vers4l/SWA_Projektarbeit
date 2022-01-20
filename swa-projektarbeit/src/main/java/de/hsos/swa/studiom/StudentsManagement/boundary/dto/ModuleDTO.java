package de.hsos.swa.studiom.StudentsManagement.boundary.dto;

import de.hsos.swa.studiom.shared.mock.MockModule;

public class ModuleDTO {
    public String name;

    public ModuleDTO(String name) {
        this.name = name;
    }

    public static class Converter {
        public static ModuleDTO toDTO(MockModule module) {
            return new ModuleDTO(module.getName());
        }

        public static MockModule toModule(ModuleDTO mDto) {
            MockModule mockModule = new MockModule();
            mockModule.setName(mDto.name);
            return mockModule;
        }
    }
}
