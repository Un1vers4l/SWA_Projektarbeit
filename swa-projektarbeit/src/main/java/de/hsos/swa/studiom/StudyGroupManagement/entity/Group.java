/**
 * @author [author]
 * @email [example@mail.com]
 * @create date 2022-01-22 14:04:44
 * @modify date 2022-01-22 20:28:46
 * @desc [description]
 */
package de.hsos.swa.studiom.StudyGroupManagement.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.shared.mock.MockModule;

@Entity
@Table(name = "groups")
@NamedQuery(name = "Groups.findAll", query = "SELECT g FROM Group g")
public class Group {
    @Id
    @SequenceGenerator(name = "groupIdSequence", sequenceName = "group_seq", allocationSize = 1, initialValue = 1001)
    @GeneratedValue(generator = "groupIdSequence")
    private int groupId;

    private int maxMembers;
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private MockModule module;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Student owner;

    @ManyToMany(mappedBy = "groups", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Student> member = new HashSet<>();

    private GroupType type;

    public Group() {
    }

    public Group(Student owner, MockModule module, String name, int maxMembers) {
        this.maxMembers = maxMembers;
        this.name = name;
        this.module = module;
        this.owner = owner;
    }

    public Group(Student owner, MockModule module, String name, int maxMembers, GroupType type) {

        this.maxMembers = maxMembers;
        this.name = name;
        this.module = module;
        this.owner = owner;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getMaxMembers() {
        return maxMembers;
    }

    public void setMaxMembers(int maxMembers) {
        this.maxMembers = maxMembers;
    }

    public MockModule getModule() {
        return module;
    }

    public void setModule(MockModule module) {
        this.module = module;
    }

    public Student getOwner() {
        return owner;
    }

    public void setOwner(Student owner) {
        this.owner = owner;
    }

    public Set<Student> getStudents() {
        return member;
    }

    public void setStudents(Set<Student> students) {
        this.member = students;
    }

    public GroupType getType() {
        return type;
    }

    public void setType(GroupType type) {
        this.type = type;
    }

    public Set<Student> getMember() {
        return member;
    }

    public void setMember(Set<Student> member) {
        this.member = member;
    }

    public void addMember(Student student) {
        this.member.add(student);
    }

    public boolean removeMember(Student student) {
        return this.member.remove(student);
    }

}
