/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 15:02:55
 * @modify date 2022-01-22 15:02:55
 * @desc [description]
 */
package de.hsos.swa.studiom.StudentsManagement.boundary.dto;

import de.hsos.swa.studiom.ModulManagment.entity.Modul;

public class ModuleDTO {
    public String name;

    public ModuleDTO(String name) {
        this.name = name;
    }

    public static class Converter {
        public static ModuleDTO toDTO(Modul module) {
            return new ModuleDTO(module.getName());
        }

        public static Modul toModule(ModuleDTO mDto) {
            Modul mockModule = new Modul();
            mockModule.setName(mDto.name);
            return mockModule;
        }
    }
}
