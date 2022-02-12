package de.hsos.swa.studiom.StudentsManagement.boundary.dto.Student;

/**
* @author Joana Wegener
* @email joana.wegener@hs-osnabrueck.de
* @create date 2022-01-22 19:20:03
* @modify date 2022-01-22 19:20:03
* @desc [description]
*/

import java.util.HashSet;
import java.util.Set;

import de.hsos.swa.studiom.ModulManagment.entity.Modul;
import de.hsos.swa.studiom.StudentsManagement.boundary.dto.Adresse.AdressDTO;
import de.hsos.swa.studiom.StudentsManagement.boundary.dto.Group.GroupDTO;
import de.hsos.swa.studiom.StudentsManagement.boundary.dto.Module.ModuleDTO;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;

public class HTTPStudentDTO {

    public int matNr;
    public String name;
    public String email;
    public Set<ModuleDTO> modules = null;
    public Set<GroupDTO> groups = null;
    public AdressDTO adress;

    public HTTPStudentDTO() {
    }

    public HTTPStudentDTO(int matNr, String name, String email, Set<ModuleDTO> modules, Set<GroupDTO> groups,
            AdressDTO adress) {
        this.matNr = matNr;
        this.name = name;
        this.email = email;
        this.modules = modules;
        this.groups = groups;
        this.adress = adress;
    }

    public static class Converter {
        public static HTTPStudentDTO toHTTPStudentDTO(Student student) {
            Set<ModuleDTO> modules = new HashSet<>();
            Set<GroupDTO> groups = new HashSet<>();

            for (Modul module : student.getModules()) {
                modules.add(ModuleDTO.Converter.toHTTPDTO(module));
            }

            for (Group group : student.getGroups()) {
                groups.add(GroupDTO.Converter.toHttpGroupDTO(group));
            }

            return new HTTPStudentDTO(student.getMatNr(), student.getFullName(), student.getEmail(), modules,
                    groups, AdressDTO.Converter.toDto(student.getAdress()));
        }
    }
}
