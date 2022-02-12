package de.hsos.swa.studiom.StudentsManagement.boundary.dto.Student;

/**
* @author Joana Wegener
* @email joana.wegener@hs-osnabrueck.de
* @create date 2022-01-22 19:20:03
* @modify date 2022-01-22 19:20:03
* @desc [description]
*/

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import de.hsos.swa.studiom.ModulManagment.boundary.dto.modul.ModulDto;
import de.hsos.swa.studiom.StudentsManagement.boundary.dto.Adresse.AdressDTO;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudyGroupManagement.boundary.dto.GroupDTO;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;

public class HTTPStudentDTO {

    public int matNr;
    public String name;
    public String email;
    public List<ModulDto> modules = null;
    public Set<GroupDTO> groups = null;
    public AdressDTO adress;

    public HTTPStudentDTO() {
    }

    public HTTPStudentDTO(int matNr, String name, String email, List<ModulDto> modules, Set<GroupDTO> groups,
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
            List<ModulDto> modules = student.getModules().stream().map(ModulDto.Converter::toMinimalHTTPModuleDTO)
                    .collect(Collectors.toList());
            Set<GroupDTO> groups = new HashSet<>();

            for (Group group : student.getGroups()) {
                groups.add(GroupDTO.Converter.toHTTPGroupDTO(group));
            }

            return new HTTPStudentDTO(student.getMatNr(), student.getFullName(), student.getEmail(), modules,
                    groups, AdressDTO.Converter.toDto(student.getAdress()));
        }
    }
}
