/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 14:13:20
 * @modify date 2022-01-31 12:00:49
 * @desc [description]
 */

package de.hsos.swa.studiom.StudentsManagement.gateway;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.TransactionRequiredException;
import javax.transaction.Transactional;
import de.hsos.swa.studiom.StudentsManagement.control.StudentService;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;

@ApplicationScoped
@Transactional
public class StudentRepository implements StudentService {

    @Inject
    EntityManager em;

    @Override
    public Optional<Student> createStudent(String name) {
        try {
            Student student = new Student(name);
            em.persist(student);
            return Optional.ofNullable(student);
        } catch (EntityExistsException | TransactionRequiredException | IllegalArgumentException e) {
            // TODO: Exception
            return Optional.ofNullable(null);
        }
    }

    @Override
    public Optional<Student> changeStudent(int matNr, Student newStudent) {
        try {
            System.out.println("OK");
            Student student = em.find(Student.class, matNr);
            student.setName(newStudent.getName());
            student.setEmail(newStudent.getEmail());
            em.persist(student);
            return Optional.ofNullable(student);
        } catch (EntityExistsException | TransactionRequiredException | IllegalArgumentException e) {
            // TODO: Exception
            return Optional.ofNullable(null);
        }
    }

    @Override
    public boolean deleteStudent(int matNr) {
        try {
            Student student = em.find(Student.class, matNr);
            em.remove(student);
            return true;
        } catch (EntityExistsException | TransactionRequiredException | IllegalArgumentException e) {
            // TODO: Exception
            return false;
        }
    }

    @Override
    public Optional<Student> getStudent(int matNr) {
        try {
            Student student = em.find(Student.class, matNr);
            return Optional.ofNullable(student);
        } catch (EntityExistsException | TransactionRequiredException | IllegalArgumentException e) {
            // TODO: Exception
            return Optional.ofNullable(null);
        }
    }

    public Optional<List<Student>> getAllStudent() {
        return Optional.ofNullable(em.createNamedQuery("Students.findAll", Student.class).getResultList());
    }
}
