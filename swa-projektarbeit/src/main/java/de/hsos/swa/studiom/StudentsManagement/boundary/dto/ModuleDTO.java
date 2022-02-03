/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 15:02:55
 * @modify date 2022-01-22 15:02:55
 * @desc [description]
 */
package de.hsos.swa.studiom.StudentsManagement.boundary.dto;

import de.hsos.swa.studiom.ModuleManagment.entity.Module;

public class ModuleDTO {
    public String name;

    public ModuleDTO(String name) {
        this.name = name;
    }

    public static class Converter {
        public static ModuleDTO toDTO(Module module) {
            return new ModuleDTO(module.getName());
        }

        public static Module toModule(ModuleDTO mDto) {
            Module mockModule = new Module();
            mockModule.setName(mDto.name);
            return mockModule;
        }
    }
}
