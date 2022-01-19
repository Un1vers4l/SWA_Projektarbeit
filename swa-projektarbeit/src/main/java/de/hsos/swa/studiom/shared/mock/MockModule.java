package de.hsos.swa.studiom.shared.mock;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;

/**
 * @author Joana Wegener
 */
@Entity
public class MockModule {
    @Id
    @SequenceGenerator(name = "mockModuleSeq", sequenceName = "mock_module_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "mockModuleSeq")
    private int id;
    private String name;

    public MockModule() {
        students = new HashSet<>();
    }

    @ManyToMany(mappedBy = "modules")
    private Set<Student> students = new HashSet<>();
}
