/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 15:02:55
 * @modify date 2022-01-22 15:02:55
 * @desc [description]
 */
package de.hsos.swa.studiom.StudentsManagement.boundary.dto;

import de.hsos.swa.studiom.shared.mock.MockModule;

public class MockModuleDTO {
    public String name;

    public MockModuleDTO(String name) {
        this.name = name;
    }

    public static class Converter {
        public static MockModuleDTO toDTO(MockModule module) {
            return new MockModuleDTO(module.getName());
        }

        public static MockModule toModule(MockModuleDTO mDto) {
            MockModule mockModule = new MockModule();
            mockModule.setName(mDto.name);
            return mockModule;
        }
    }
}
