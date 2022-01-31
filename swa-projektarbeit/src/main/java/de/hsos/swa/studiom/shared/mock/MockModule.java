/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 19:23:29
 * @modify date 2022-01-31 08:39:55
 * @desc [description]
 */

package de.hsos.swa.studiom.shared.mock;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Generated;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;

@Entity
@ApplicationScoped
public class MockModule {
    @Id
    @SequenceGenerator(name = "mockModuleSeq", sequenceName = "mock_module_seq", allocationSize = 1, initialValue = 6)
    @GeneratedValue(generator = "mockModuleSeq")
    private int id;
    private String name;
    private boolean isProject;

    public MockModule() {
        students = new HashSet<>();
    }

    @ManyToMany(mappedBy = "modules")
    private Set<Student> students = new HashSet<>();

    public String getName() {
        return this.name;
    }

    public MockModule(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isProject() {
        return isProject;
    }

    public void setProject(boolean isProject) {
        this.isProject = isProject;
    }

    @Override
    public String toString() {
        return "MockModule [id=" + id + ", name=" + name + "]";
    }

}
