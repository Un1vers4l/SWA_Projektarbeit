package de.hsos.swa.studiom.StudentsManagement.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
    @SequenceGenerator(name = "matNrSequence", sequenceName = "students_seq", allocationSize = 1, initialValue = 100000)
    @GeneratedValue(generator = "matNrSequence")
    private int matNr;
    private String name;
    private String email;

    @ManyToMany
    @JoinTable(name = "students_modules", joinColumns = { @JoinColumn(name = "fk_student") }, inverseJoinColumns = {
            @JoinColumn(name = "fk_module") })
    private Set<MockModule> modules = new HashSet<>();

    @ManyToMany
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

    public void addAdress(String street, int nr, int zipCode, String town) {
        this.adress = new Adress(street, nr, zipCode, town);
    }

    public Student(String name) {
        this.name = name;
        this.email = this.name + "@hs-osnabrueck.de";
        this.groups = new HashSet<>();
    }

}
