/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 19:20:03
 * @modify date 2022-01-22 19:20:03
 * @desc [description]
 */
package de.hsos.swa.studiom.StudentsManagement.boundary.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.hsos.swa.studiom.ModulManagment.entity.Modul;
import de.hsos.swa.studiom.StudentsManagement.entity.Adress;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;

public class StudentDTO {
    public int matNr;
    public String name;
    public String email;
    public Set<ModuleDTO> modules = null;
    public Set<MockGroupDTO> groups = null;
    public AdressDTO adress;


    public StudentDTO() {
    }


    public StudentDTO(int matNr, String name, String email, Set<ModuleDTO> modules, Set<MockGroupDTO> groups,
            AdressDTO adress) {
        this.matNr = matNr;
        this.name = name;
        this.email = email;
        this.modules = modules;
        this.groups = groups;
        this.adress = adress;
    }


    public int getMatNr() {
        return this.matNr;
    }

    public void setMatNr(int matNr) {
        this.matNr = matNr;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<ModuleDTO> getModules() {
        return this.modules;
    }

    public void setModules(Set<ModuleDTO> modules) {
        this.modules = modules;
    }

    public Set<MockGroupDTO> getGroups() {
        return this.groups;
    }

    public void setGroups(Set<MockGroupDTO> groups) {
        this.groups = groups;
    }

    public AdressDTO getAdress() {
        return this.adress;
    }

    public void setAdress(AdressDTO adress) {
        this.adress = adress;
    }


    public static class Converter {
        public static StudentDTO toStudentDTO(Student student) {
            Set<ModuleDTO> modules = new HashSet<>();
            Set<MockGroupDTO> groups = new HashSet<>();

            for (Modul module : student.getModules()) {
                modules.add(ModuleDTO.Converter.toDTO(module));
            }

            for (Group group : student.getGroups()) {
                groups.add(MockGroupDTO.Converter.toDTO(group));
            }

            return new StudentDTO(student.getMatNr(), student.getFullName(), student.getEmail(), modules,
                    groups, AdressDTO.Converter.toDto(student.getAdress()));
        }
    }
}
