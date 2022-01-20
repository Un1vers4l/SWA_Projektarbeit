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

import de.hsos.swa.studiom.shared.mock.MockGroup;
import de.hsos.swa.studiom.shared.mock.MockModule;

/**
 * @author Joana Wegener
 */

@Entity
@Table(name = "students")
@NamedQuery(name = "Students.findAll", query = "SELECT s FROM Student s")
public class Student {
    @Id
    @SequenceGenerator(name = "matNrSequence", sequenceName = "students_seq", allocationSize = 1, initialValue = 1000)
    @GeneratedValue(generator = "matNrSequence")
    private int matNr;
    private String name;
    private String email;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "students_modules", joinColumns = { @JoinColumn(name = "fk_student") }, inverseJoinColumns = {
            @JoinColumn(name = "fk_module") })
    private Set<MockModule> modules = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "students_groups", joinColumns = { @JoinColumn(name = "fk_student") }, inverseJoinColumns = {
            @JoinColumn(name = "fk_group") })
    private Set<MockGroup> groups = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Adress adress;

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

    public Set<MockModule> getModules() {
        return modules;
    }

    public void setModules(Set<MockModule> modules) {
        this.modules = modules;
    }

    public Set<MockGroup> getGroups() {
        return groups;
    }

    public void setGroups(Set<MockGroup> groups) {
        this.groups = groups;
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

    public Student(int matNr, String name, String email, Set<MockModule> modules, Set<MockGroup> groups,
            Adress adress) {
        this.matNr = matNr;
        this.name = name;
        this.email = email;
        this.modules = modules;
        this.groups = groups;
        this.adress = adress;
    }

}
