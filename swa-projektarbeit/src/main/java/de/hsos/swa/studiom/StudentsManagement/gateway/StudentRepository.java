package de.hsos.swa.studiom.StudentsManagement.gateway;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.TransactionRequiredException;
import javax.transaction.Transactional;

import de.hsos.swa.studiom.StudentsManagement.control.AddressService;
import de.hsos.swa.studiom.StudentsManagement.control.StudentService;
import de.hsos.swa.studiom.StudentsManagement.entity.Adress;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;

/**
 * @author Joana Wegener
 */

@ApplicationScoped
@Transactional
public class StudentRepository implements StudentService, AddressService {

    @Inject
    EntityManager em;

    @Override
    public Optional<Adress> createAdress(int matNr, Adress adress) {
        try {
            Student student = em.find(Student.class, matNr);
            if (student.getAdress() != null) {
                return Optional.ofNullable(null);
            }
            student.setAdress(adress);
            em.persist(student);
            return Optional.ofNullable(adress);
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            return Optional.ofNullable(null);
        }
    }

    @Override
    public Optional<Adress> getAdress(int matNr) {
        try {
            Student student = em.find(Student.class, matNr);
            if (student.getAdress() == null) {
                return Optional.ofNullable(null);
            }
            return Optional.ofNullable(student.getAdress());
        } catch (EntityExistsException | TransactionRequiredException | IllegalArgumentException e) {
            return Optional.ofNullable(null);
        }
    }

    @Override
    public boolean deleteAdress(int matNr) {
        try {
            Student student = em.find(Student.class, matNr);
            if (student.getAdress() == null) {
                return false;
            }
            student.setAdress(null);
            em.persist(student);
            return true;
        } catch (EntityExistsException | TransactionRequiredException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public Optional<Adress> changeAdress(int matNr, Adress adress) {
        try {
            Student student = em.find(Student.class, matNr);
            if (student.getAdress() == null) {
                return Optional.ofNullable(null);
            }
            student.setAdress(adress);
            em.persist(student);
            return Optional.ofNullable(adress);
        } catch (EntityExistsException | TransactionRequiredException | IllegalArgumentException e) {
            return Optional.ofNullable(null);
        }
    }

    @Override
    public Optional<Student> createStudent(String name) {
        try {
            Student student = new Student(name);
            em.persist(student);
            return Optional.ofNullable(student);
        } catch (EntityExistsException | TransactionRequiredException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return Optional.ofNullable(null);
        }
    }

    @Override
    public Optional<Student> changeStudent(Student newStudent) {
        try {
            Student student = em.find(Student.class, newStudent.getMatNr());
            student = newStudent;
            em.persist(student);
            return Optional.ofNullable(student);
        } catch (EntityExistsException | TransactionRequiredException | IllegalArgumentException e) {
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
            return false;
        }
    }

    @Override
    public Optional<Student> getStudent(int matNr) {
        try {
            Student student = em.find(Student.class, matNr);
            return Optional.ofNullable(student);
        } catch (EntityExistsException | TransactionRequiredException | IllegalArgumentException e) {
            return Optional.ofNullable(null);
        }
    }

    public Optional<List<Student>> getAllStudent() {
        return Optional.ofNullable(em.createNamedQuery("Students.findAll", Student.class).getResultList());
    }
}
