/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-31 11:59:30
 * @modify date 2022-01-31 12:03:51
 * @desc [description]
 */
package de.hsos.swa.studiom.StudentsManagement.gateway;

import de.hsos.swa.studiom.StudentsManagement.control.AddressService;
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

@ApplicationScoped
@Transactional
public class AdressRepository implements AddressService {

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
            // TODO: Exception
            return Optional.ofNullable(null);
        }
    }

    @Override
    public Optional<Adress> getAdress(int matNr) {
        try {
            Student student = em.find(Student.class, matNr);
            if (student.getAdress() == null) {
                // TODO: Exception: Keine Adresse gefunden
                return Optional.ofNullable(null);
            }
            return Optional.ofNullable(student.getAdress());
        } catch (EntityExistsException | TransactionRequiredException | IllegalArgumentException e) {
            // TODO: Exception
            return Optional.ofNullable(null);
        }
    }

    @Override
    public boolean deleteAdress(int matNr) {
        try {
            Student student = em.find(Student.class, matNr);
            if (student.getAdress() == null) {
                // TODO: Exception: Keine Adresse gefundent
                return false;
            }
            student.setAdress(null);
            em.persist(student);
            return true;
        } catch (EntityExistsException | TransactionRequiredException | IllegalArgumentException e) {
            // TODO: Exception
            return false;
        }
    }

    @Override
    public Optional<Adress> changeAdress(int matNr, Adress adress) {
        try {
            Student student = em.find(Student.class, matNr);
            if (student.getAdress() == null) {
                // TODO: Exception: Keine Adresse gefunden
                return Optional.ofNullable(null);
            }
            student.setAdress(adress);
            em.persist(student);
            return Optional.ofNullable(adress);
        } catch (EntityExistsException | TransactionRequiredException | IllegalArgumentException e) {
            // TODO: Exception
            return Optional.ofNullable(null);
        }
    }
}
