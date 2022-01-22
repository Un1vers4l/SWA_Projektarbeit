package de.hsos.swa.studiom.StudentsManagement.boundary.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.hsos.swa.studiom.StudentsManagement.entity.Adress;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;
import de.hsos.swa.studiom.shared.mock.MockGroup;
import de.hsos.swa.studiom.shared.mock.MockModule;

/**
 * @author Joana Wegener
 */
public class StudentDTO {
    public int matNr;
    public String name;
    public String email;
    public Set<MockModuleDTO> modules = new HashSet<>();
    public Set<MockGroupDTO> groups = new HashSet<>();
    public AdressDTO adress;

    public StudentDTO(int matNr, String name, String email, Set<MockModuleDTO> modules, Set<MockGroupDTO> groups,
            AdressDTO adress) {
        this.matNr = matNr;
        this.name = name;
        this.email = email;
        this.modules = modules;
        this.groups = groups;
        this.adress = adress;
    }

    public static class Converter {
        public static StudentDTO toStudentDTO(Student student) {
            Set<MockModuleDTO> modules = new HashSet<>();
            Set<MockGroupDTO> groups = new HashSet<>();

            for (MockModule module : student.getModules()) {
                modules.add(MockModuleDTO.Converter.toDTO(module));
            }

            for (Group group : student.getGroups()) {
                groups.add(MockGroupDTO.Converter.toDTO(group));
            }

            return new StudentDTO(student.getMatNr(), student.getName(), student.getEmail(), modules,
                    groups, AdressDTO.Converter.toDto(student.getAdress()));
        }
        /*
         * public static Student toStudent(StudentDTO sDto) {
         * return new Student(sDto.matNr, sDto.name, sDto.email, sDto.modules,
         * sDto.groups, sDto.adress);
         * }
         */
    }
}
