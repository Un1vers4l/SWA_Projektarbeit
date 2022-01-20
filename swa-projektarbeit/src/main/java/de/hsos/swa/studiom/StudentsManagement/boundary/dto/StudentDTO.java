package de.hsos.swa.studiom.StudentsManagement.boundary.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.hsos.swa.studiom.StudentsManagement.entity.Adress;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.shared.mock.MockGroup;
import de.hsos.swa.studiom.shared.mock.MockModule;

public class StudentDTO {
    public int matNr;
    public String name;
    public String email;
    public Set<ModuleDTO> modules = new HashSet<>();
    public Set<GroupDTO> groups = new HashSet<>();
    public Adress adress;

    public StudentDTO(int matNr, String name, String email, Set<ModuleDTO> modules, Set<GroupDTO> groups,
            Adress adress) {
        this.matNr = matNr;
        this.name = name;
        this.email = email;
        this.modules = modules;
        this.groups = groups;
        this.adress = adress;
    }

    public static class Converter {
        public static StudentDTO toStudentDTO(Student student) {
            Set<ModuleDTO> modules = new HashSet<>();
            Set<GroupDTO> groups = new HashSet<>();

            for (MockModule module : student.getModules()) {
                modules.add(ModuleDTO.Converter.toDTO(module));
            }

            for (MockGroup group : student.getGroups()) {
                groups.add(GroupDTO.Converter.toDTO(group));
            }

            return new StudentDTO(student.getMatNr(), student.getName(), student.getEmail(), modules,
                    groups, student.getAdress());
        }
        /*
         * public static Student toStudent(StudentDTO sDto) {
         * return new Student(sDto.matNr, sDto.name, sDto.email, sDto.modules,
         * sDto.groups, sDto.adress);
         * }
         */
    }
}
