/**
 * @author Joana Wegener (855518)
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-02-11 09:30:17
 * @modify date 2022-02-11 09:30:17
 * @desc [description]
 */
package de.hsos.swa.studiom.StudentsManagement.boundary.dto.Group;

import java.util.HashSet;
import java.util.Set;

import de.hsos.swa.studiom.StudentsManagement.boundary.dto.Module.ModuleDTO;
import de.hsos.swa.studiom.StudentsManagement.boundary.dto.Student.HTTPStudentMin;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;
import de.hsos.swa.studiom.StudyGroupManagement.entity.GroupType;

public class GroupDTO {

    public int id;
    public String name;
    public int maxMembers;
    public ModuleDTO module;
    public HTTPStudentMin owner;
    public Set<HTTPStudentMin> member;
    public GroupType type;

    public GroupDTO(int id, String name, int maxMembers, ModuleDTO module, HTTPStudentMin owner, GroupType type) {
        member = new HashSet<>();
        this.id = id;
        this.name = name;
        this.maxMembers = maxMembers;
        this.module = module;
        this.owner = owner;
        this.type = type;
    }

    public GroupDTO() {
    }

    public static class Converter {
        public static GroupDTO toHttpGroupDTO(Group group) {
            GroupDTO httpGroup = new GroupDTO(group.getGroupId(), group.getName(), group.getMaxMembers(),
                    ModuleDTO.Converter.toHTTPDTO(group.getModul()), HTTPStudentMin.Converter.toDTO(group.getOwner()),
                    group.getType());
            for (Student student : group.getMember()) {
                httpGroup.member.add(HTTPStudentMin.Converter.toDTO(student));
            }
            return httpGroup;
        }

        public static GroupDTO toSimpleGroupDTO(Group group) {
            GroupDTO httpGroup = new GroupDTO();
            httpGroup.id = group.getGroupId();
            httpGroup.name = group.getName();
            httpGroup.maxMembers = group.getMaxMembers();
            return httpGroup;
        }

    }
}
