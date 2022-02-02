package de.hsos.swa.studiom.ModuleManagment.entity;

import java.util.Set;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.QueryHint;
import javax.persistence.SequenceGenerator;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;

@Vetoed
@Entity
@NamedQuery(name = "Module.findAll", query = "SELECT f from Module f", hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
public class Module {
    @Id
    @SequenceGenerator(name = "moduleIdSequence", sequenceName = "Modules_seq", allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "moduleIdSequence")
    private int moduleID;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 512)
    private String description;

    @Column(nullable = false)
    private boolean isProject;

    @ManyToMany(mappedBy = "modules")
    private Set<Student> studenten;


    @OneToMany(mappedBy="module")
    private Set<Group> items;

    public Module() {
    }


    public Module(int moduleID, String name, String description, boolean isProject, Set<Student> studenten) {
        this.moduleID = moduleID;
        this.name = name;
        this.description = description;
        this.isProject = isProject;
        this.studenten = studenten;
    }


    public int getModuleID() {
        return this.moduleID;
    }

    public void setModuleID(int moduleID) {
        this.moduleID = moduleID;
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

    public boolean isIsProject() {
        return this.isProject;
    }

    public boolean getIsProject() {
        return this.isProject;
    }

    public void setIsProject(boolean isProject) {
        this.isProject = isProject;
    }

    public Set<Student> getStudenten() {
        return this.studenten;
    }

    public void setStudenten(Set<Student> studenten) {
        this.studenten = studenten;
    }

    public boolean isProject() {
        return this.isProject;
    }

}
