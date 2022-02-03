/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 14:08:13
 * @modify date 2022-01-31 08:38:24
 * @desc [description]
 */
package de.hsos.swa.studiom.StudentsManagement.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import de.hsos.swa.studiom.ModuleManagment.entity.Module;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;
import de.hsos.swa.studiom.UserManagement.entity.User;

@Entity
@Table(name = "students")
@NamedQuery(name = "Students.findAll", query = "SELECT s FROM Student s")
public class Student {
    @Id
    @SequenceGenerator(name = "matNrSequence", sequenceName = "students_seq", allocationSize = 1, initialValue = 1004)
    @GeneratedValue(generator = "matNrSequence")
    private int matNr;
    private String name;
    private String email;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "student_modules", joinColumns = { @JoinColumn(name = "fk_student") }, inverseJoinColumns = {
            @JoinColumn(name = "fk_module") })
    private Set<Module> modules = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "student_groups", joinColumns = { @JoinColumn(name = "fk_student") }, inverseJoinColumns = {
            @JoinColumn(name = "fk_group") })
    private Set<Group> groups = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Adress adress;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", unique = true, nullable = false)
    private User user;

    public Student() {
    }

    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Student(String name) {
        this.name = name.trim();
        this.email = generateEmail(name);
    }

    public String generateEmail(String name) {
        name = name.trim();
        name = name.replace(" ", ".");
        name = name.toLowerCase();
        return name + "@hs-osnabrueck.de";
    }

    @Override
    public String toString() {
        return "Student [adress=" + adress + ", email=" + email + ", groups=" + groups + ", matNr=" + matNr
                + ", modules=" + modules + ", name=" + name + "]";
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public int getMatNr() {
        return matNr;
    }

    public void setMatNr(int matNr) {
        this.matNr = matNr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Module> getModules() {
        return modules;
    }

    public void setModules(Set<Module> modules) {
        this.modules = modules;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public void addGroup(Group group) {
        this.groups.add(group);
    }

    public boolean removeGroup(Group group) {
        return this.groups.remove(group);
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    public void addAdress(String street, int nr, int zipCode, String town) {
        this.adress = new Adress(street, nr, zipCode, town);
    }

    public Student(int matNr, String name, String email, Set<Module> modules, Set<Group> groups,
            Adress adress) {
        this.matNr = matNr;
        this.name = name;
        this.email = email;
        this.modules = modules;
        this.groups = groups;
        this.adress = adress;
    }

}
