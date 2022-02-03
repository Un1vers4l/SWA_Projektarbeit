/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 14:13:20
 * @modify date 2022-02-02 08:36:30
 * @desc [description]
 */

package de.hsos.swa.studiom.StudentsManagement.gateway;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.TransactionRequiredException;
import javax.transaction.Transactional;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.jboss.logging.Logger;

import de.hsos.swa.studiom.StudentsManagement.control.StudentService;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.UserManagement.control.UserService;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;

@RequestScoped
@Transactional
public class StudentRepository implements StudentService {

    Logger log = Logger.getLogger(StudentRepository.class);

    @Context
    UriInfo uriInfo;

    @Inject
    EntityManager em;

    @Inject
    UserService userService;

    @Override
    public Optional<Student> createStudent(String name) {
        try {
            Student student = new Student(name);
            em.persist(student);
            return Optional.ofNullable(student);
        } catch (EntityExistsException | TransactionRequiredException | IllegalArgumentException e) {
            log.error("Eine Exception wurde geworfen \n" + e.toString());
            return Optional.ofNullable(null);
        }
    }

    @Override
    public Optional<Student> changeStudent(int matNr, Student newStudent) throws EntityNotFoundException {
        try {
            Student student = getStudent(matNr).get();
            if (student == null) {
                return Optional.ofNullable(null);
            }
            student.setName(newStudent.getName());
            student.setEmail(newStudent.getEmail());
            em.persist(student);
            return Optional.ofNullable(student);
        } catch (EntityExistsException | TransactionRequiredException | IllegalArgumentException e) {
            log.error("Eine Exception wurde geworfen \n" + e.toString());
            return Optional.ofNullable(null);
        }
    }

    @Override
    public boolean deleteStudent(int matNr) throws EntityNotFoundException {
        try {
            Student student = getStudent(matNr).get();
            if (student == null) {
                return false;
            }
            em.remove(student);
            return true;
        } catch (TransactionRequiredException | IllegalArgumentException e) {
            log.error("Eine Exception wurde geworfen \n" + e.toString());
            return false;
        }
    }

    @Override
    public Optional<Student> getStudent(int matNr) throws EntityNotFoundException {
        try {
            Student student = em.find(Student.class, matNr);
            if (student == null) {
                log.error("Student konnte nicht gefunden werden");
                throw new EntityNotFoundException(Student.class, matNr);
            }
            return Optional.ofNullable(student);
        } catch (IllegalArgumentException e) {
            log.error("Eine Exception wurde geworfen \n" + e.toString());
            return Optional.ofNullable(null);
        }
    }

    public Optional<List<Student>> getAllStudent() {
        return Optional.ofNullable(em.createNamedQuery("Students.findAll", Student.class).getResultList());
    }
}
