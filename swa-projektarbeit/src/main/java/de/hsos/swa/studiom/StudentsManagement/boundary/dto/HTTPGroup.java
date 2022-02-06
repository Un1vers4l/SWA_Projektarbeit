package de.hsos.swa.studiom.StudentsManagement.boundary.dto;

import java.util.HashSet;
import java.util.Set;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;
import de.hsos.swa.studiom.StudyGroupManagement.entity.GroupType;

public class HTTPGroup {

    public int id;
    public String name;
    public int maxMembers;
    public HTTPModule module;
    public HTTPStudentMin owner;
    public Set<HTTPStudentMin> member = new HashSet<>();
    public GroupType type;

    public HTTPGroup(int id, String name, int maxMembers, HTTPModule module, HTTPStudentMin owner, GroupType type) {
        this.id = id;
        this.name = name;
        this.maxMembers = maxMembers;
        this.module = module;
        this.owner = owner;
        this.type = type;
    }

    
    public HTTPGroup() {
    }


    public static class Converter {
        public static HTTPGroup toDTO(Group group) {
            HTTPGroup httpGroup = new HTTPGroup(group.getGroupId(), group.getName(), group.getMaxMembers(),
                    HTTPModule.Converter.toDTO(group.getModul()), HTTPStudentMin.Converter.toDTO(group.getOwner()),
                    group.getType());
            for (Student student : group.getMember()) {
                httpGroup.member.add(HTTPStudentMin.Converter.toDTO(student));
            }
            return httpGroup;
        }
    }
}
