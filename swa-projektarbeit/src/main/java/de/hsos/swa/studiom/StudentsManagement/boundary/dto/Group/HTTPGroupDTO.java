package de.hsos.swa.studiom.StudentsManagement.boundary.dto.Group;

import java.util.HashSet;
import java.util.Set;

import de.hsos.swa.studiom.StudentsManagement.boundary.dto.Module.ModuleDTO;
import de.hsos.swa.studiom.StudentsManagement.boundary.dto.Student.HTTPStudentMin;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;
import de.hsos.swa.studiom.StudyGroupManagement.entity.GroupType;

public class HTTPGroupDTO {

    public int id;
    public String name;
    public int maxMembers;
    public ModuleDTO module;
    public HTTPStudentMin owner;
    public Set<HTTPStudentMin> member = new HashSet<>();
    public GroupType type;

    public HTTPGroupDTO(int id, String name, int maxMembers, ModuleDTO module, HTTPStudentMin owner, GroupType type) {
        this.id = id;
        this.name = name;
        this.maxMembers = maxMembers;
        this.module = module;
        this.owner = owner;
        this.type = type;
    }
    
    public HTTPGroupDTO() {
    }


    public static class Converter {
        public static HTTPGroupDTO toDTO(Group group) {
            HTTPGroupDTO httpGroup = new HTTPGroupDTO(group.getGroupId(), group.getName(), group.getMaxMembers(),
                    ModuleDTO.Converter.toHTTPDTO(group.getModul()), HTTPStudentMin.Converter.toDTO(group.getOwner()),
                    group.getType());
            for (Student student : group.getMember()) {
                httpGroup.member.add(HTTPStudentMin.Converter.toDTO(student));
            }
            return httpGroup;
        }

        
    }
}
