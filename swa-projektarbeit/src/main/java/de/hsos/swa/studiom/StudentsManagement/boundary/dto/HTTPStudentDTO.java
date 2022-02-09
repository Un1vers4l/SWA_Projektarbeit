package de.hsos.swa.studiom.StudentsManagement.boundary.dto;

/**
* @author Joana Wegener
* @email joana.wegener@hs-osnabrueck.de
* @create date 2022-01-22 19:20:03
* @modify date 2022-01-22 19:20:03
* @desc [description]
*/

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.hsos.swa.studiom.ModulManagment.entity.Modul;
import de.hsos.swa.studiom.StudentsManagement.entity.Adress;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;

public class HTTPStudentDTO {

    public int matNr;
    public String name;
    public String email;
    public Set<HTTPModule> modules = null;
    public Set<HTTPGroup> groups = null;
    public AdressDTO adress;

    public HTTPStudentDTO() {
    }

    public HTTPStudentDTO(int matNr, String name, String email, Set<HTTPModule> modules, Set<HTTPGroup> groups,
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
            Set<HTTPModule> modules = new HashSet<>();
            Set<HTTPGroup> groups = new HashSet<>();

            for (Modul module : student.getModules()) {
                modules.add(HTTPModule.Converter.toDTO(module));
            }

            for (Group group : student.getGroups()) {
                groups.add(HTTPGroup.Converter.toDTO(group));
            }

            return new HTTPStudentDTO(student.getMatNr(), student.getFullName(), student.getEmail(), modules,
                    groups, AdressDTO.Converter.toDto(student.getAdress()));
        }
    }
}
