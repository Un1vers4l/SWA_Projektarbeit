/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-02-02 22:08:37
 * @modify date 2022-02-02 22:08:37
 * @desc [description]
 */
package de.hsos.swa.studiom.ModulManagment.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.enterprise.inject.Vetoed;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.QueryHint;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;

@Vetoed
@Entity
@Table(name = "Module")
@NamedQuery(name = "Modul.findAll", query = "SELECT f from Modul f", hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
public class Modul {
    @Id
    @SequenceGenerator(name = "moduleIdSequence", sequenceName = "Modules_seq", allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "moduleIdSequence")
    private int modulID;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private Boolean isProject;

    @ManyToMany(mappedBy = "modules", fetch = FetchType.LAZY)
    private Set<Student> studenten = new HashSet<>();

    @OneToMany(mappedBy = "modul", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Group> projects = new HashSet<>();

    @OneToMany(mappedBy = "modul", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Question> questions = new HashSet<>();

    public Modul() {
    }

    public Modul(String name, String description, Boolean isProject) {
        this.setName(name);
        this.setDescription(description);
        this.setIsProject(isProject);
    }

    public int getModulID() {
        return this.modulID;
    }

    public void setModulID(int moduleID) {
        this.modulID = moduleID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isIsProject() {
        return this.isProject;
    }

    public Boolean getIsProject() {
        return this.isProject;
    }

    public void setIsProject(Boolean isProject) {
        this.isProject = isProject;
    }

    public Set<Student> getStudenten() {
        return this.studenten;
    }

    public void setStudenten(Set<Student> studenten) {
        this.studenten = studenten;
    }

    public Set<Group> getProjects() {
        return this.projects;
    }

    public void setProjects(Set<Group> projects) {
        this.projects = projects;
    }

    public Set<Question> getQuestions() {
        return this.questions;
    }

    public void setQuestions(Set<Question> question) {
        this.questions = question;
    }

    public boolean isProject() {
        return this.isProject;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Modul)) {
            return false;
        }
        Modul module = (Modul) o;
        return modulID == module.modulID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(modulID);
    }

    @Override
    public String toString() {
        return "{" +
                " moduleID='" + getModulID() + "'" +
                ", name='" + getName() + "'" +
                ", description='" + getDescription() + "'" +
                ", isProject='" + isIsProject() + "'" +
                "}";
    }

    public void changeMyData(Modul other) {
        if (other.name != null)
            this.name = new String(other.name);
        if (other.description != null)
            this.description = new String(other.description);
    }

    public int studentenAnzahl() {
        return this.studenten.size();
    }

    public boolean removeMember(Student student){
        return this.studenten.remove(student);
    }
}
