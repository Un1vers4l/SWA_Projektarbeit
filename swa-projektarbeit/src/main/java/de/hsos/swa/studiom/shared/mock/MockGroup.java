/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 14:36:32
 * @modify date 2022-01-22 14:36:32
 * @desc [description]
 */
package de.hsos.swa.studiom.shared.mock;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;

@Entity
@RequestScoped
public class MockGroup {
    @Id
    @SequenceGenerator(name = "mockGroupSeq", sequenceName = "mock_group_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "mockGroupSeq")
    private int id;
    private String name;

    public MockGroup() {

    }

    public MockGroup(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "groups")
    private Set<Student> students = new HashSet<>();

    public MockGroup getMockGroup() {
        return new MockGroup("Testname");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
