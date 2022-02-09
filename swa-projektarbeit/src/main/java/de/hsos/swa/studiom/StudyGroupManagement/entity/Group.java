/**
 * @author [author]
 * @email [example@mail.com]
 * @create date 2022-01-22 14:04:44
 * @modify date 2022-01-31 08:38:29
 * @desc [description]
 */
package de.hsos.swa.studiom.StudyGroupManagement.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import de.hsos.swa.studiom.ModulManagment.entity.Modul;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;
@Vetoed
@Entity
@Table(name = "groups")
@NamedQuery(name = "Groups.findAll", query = "SELECT g FROM Group g")
public class Group {
    @Id
    @SequenceGenerator(name = "groupIdSequence", sequenceName = "group_seq", allocationSize = 1, initialValue = 1003)
    @GeneratedValue(generator = "groupIdSequence")
    private int groupId;

    @Column(nullable = false)
    private int maxMembers;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Modul modul;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Student owner;

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
    private Set<Student> member = new HashSet<>();

    private GroupType type;

    public Group() {
    }

    public Group(Student owner, Modul module, String name, int maxMembers) {
        this.maxMembers = maxMembers;
        this.name = name;
        this.modul = module;
        this.owner = owner;
    }

    public Group(Student owner, Modul modul, String name, int maxMembers, GroupType type) {
        this.maxMembers = maxMembers;
        this.name = name;
        this.modul = modul;
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

    public Modul getModul() {
        return modul;
    }

    public void setModul(Modul module) {
        this.modul = module;
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

    public boolean addMember(Student student) {
        return this.member.add(student);
    }

    public boolean removeMember(Student student) {
        return this.member.remove(student);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Group)) {
            return false;
        }
        Group group = (Group) o;
        return groupId == group.groupId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId);
    }

    @Override
    public String toString() {
        return "{" +
            " groupId='" + getGroupId() + "'" +
            ", maxMembers='" + getMaxMembers() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }


}
