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

import javax.enterprise.inject.Vetoed;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import de.hsos.swa.studiom.ModulManagment.entity.Answer;
import de.hsos.swa.studiom.ModulManagment.entity.Modul;
import de.hsos.swa.studiom.ModulManagment.entity.Question;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;
import de.hsos.swa.studiom.UserManagement.entity.User;

@Vetoed
@Entity
@Table(name = "students")
@NamedQuery(name = "Students.findAll", query = "SELECT s FROM Student s")
public class Student {
    @Id
    @SequenceGenerator(name = "matNrSequence", sequenceName = "students_seq", allocationSize = 1, initialValue = 1004)
    @GeneratedValue(generator = "matNrSequence")
    private int matNr;
    private String vorname;
    private String nachname;
    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "student_modules", joinColumns = { @JoinColumn(name = "fk_student") }, inverseJoinColumns = {
            @JoinColumn(name = "fk_modul") })
    private Set<Modul> modules = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "student_groups", joinColumns = { @JoinColumn(name = "fk_student") }, inverseJoinColumns = {
            @JoinColumn(name = "fk_group") })
    private Set<Group> groups = new HashSet<>();

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Adress adress;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", unique = true, nullable = false)
    private User user;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Group> myGroups = new HashSet<>();

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private Set<Question> myQuestion = new HashSet<>();

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private Set<Answer> myAnswer = new HashSet<>();

    public Student() {
    }

    public Student(String vorname,String nachname, String email) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
    }

    public Student(String vorname, String nachname) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = generateEmail(vorname, nachname);
    }

    public String generateEmail(String vorname, String nachname) {
        StringBuilder sb = new StringBuilder();
        sb.append(vorname.toLowerCase());
        sb.append(".");
        sb.append(vorname.toLowerCase());
        sb.append("@hs-osnabrueck.de");
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Student [adress=" + adress + ", email=" + email + ", matNr=" + matNr
                + ", vorname=" + vorname + ", nachname= " + nachname + "]";
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

    public String getVorname() {
        return this.vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return this.nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getEmail() {
        return email;
    }

    public Set<Group> getMyGroups() {
        return this.myGroups;
    }

    public void setMyGroups(Set<Group> myGroups) {
        this.myGroups = myGroups;
    }

    public Set<Question> getMyQuestion() {
        return this.myQuestion;
    }

    public void setMyQuestion(Set<Question> myQuestion) {
        this.myQuestion = myQuestion;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Modul> getModules() {
        return modules;
    }

    public void setModules(Set<Modul> modules) {
        this.modules = modules;
    }

    public Set<Group> getGroups() {
        return this.groups;
    }

    public boolean addModule(Modul module) {
        return this.modules.add(module);
    }
    public boolean removeModule(Modul module) {
        return this.modules.remove(module);
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

    public Set<Answer> getMyAnswer() {
        return this.myAnswer;
    }

    public void setMyAnswer(Set<Answer> myAnswer) {
        this.myAnswer = myAnswer;
    }

    public void addAdress(String street, int nr, int zipCode, String town) {
        this.adress = new Adress(street, nr, zipCode, town);
    }

    public Student(int matNr, String vorname, String nachname, String email, Set<Modul> modules, Set<Group> groups,
            Adress adress) {
        this.matNr = matNr;
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.modules = modules;
        this.groups = groups;
        this.adress = adress;
    }

    public String getFullName() {
        return this.vorname + " " + this.nachname;
    }
}
