/**
 * @author Joana Wegener (855518)
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 20:11:37
 * @modify date 2022-02-12 14:11:12
 * @desc [description]
 */
package de.hsos.swa.studiom.StudyGroupManagement.boundary.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import de.hsos.swa.studiom.ModulManagment.boundary.dto.modul.ModulDto;
import de.hsos.swa.studiom.StudentsManagement.boundary.dto.Student.StudentDTO;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;
import de.hsos.swa.studiom.StudyGroupManagement.entity.GroupType;

public class GroupDTO {
    public int id;
    public StudentDTO owner;
    public String name;
    @JsonInclude(Include.NON_NULL)
    public Integer maxMember;
    public ModulDto module;
    public List<StudentDTO> member;
    public GroupType type;

    public GroupDTO() {
    }

    public GroupDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public GroupDTO(int id, StudentDTO owner, String name, Integer maxMember, ModulDto module,
            List<StudentDTO> members) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.maxMember = maxMember;
        this.module = module;
        this.member = members;
    }

    public GroupDTO(int id, StudentDTO owner, String name, Integer maxMember, ModulDto module, List<StudentDTO> members,
            GroupType type) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.maxMember = maxMember;
        this.module = module;
        this.member = members;
        this.type = type;
    }

    public static class Converter {
        public static GroupDTO toSimpleGroupDTO(Group group) {
            List<StudentDTO> member = group.getMember().stream().map(StudentDTO.Converter::toMinimalStudentDTO)
                    .collect(Collectors.toList());
            return new GroupDTO(group.getGroupId(), StudentDTO.Converter.toMinimalStudentDTO(group.getOwner()),
                    group.getName(),
                    group.getMaxMembers(), ModulDto.Converter.toMinimalModuleDTO(group.getModul()), member);
        }

        public static GroupDTO toMinimalGroupDTO(Group group) {
            return new GroupDTO(group.getGroupId(), group.getName());
        }

        public static GroupDTO toHTTPGroupDTO(Group group) {
            List<StudentDTO> member = group.getMember().stream().map(StudentDTO.Converter::toMinimalStudentDTO)
                    .collect(Collectors.toList());
            return new GroupDTO(group.getGroupId(), StudentDTO.Converter.toMinimalStudentDTO(group.getOwner()),
                    group.getName(), group.getMaxMembers(), ModulDto.Converter.toMinimalHTTPModuleDTO(group.getModul()),
                    member, group.getType());
        }
    }

}
