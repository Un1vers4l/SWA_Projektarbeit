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

import org.jboss.logging.Logger;

import de.hsos.swa.studiom.StudentsManagement.entity.Adress;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;

@ApplicationScoped
@Transactional
public class AdressRepository implements AddressService {

    Logger log = Logger.getLogger(AdressRepository.class);

    @Inject
    EntityManager em;

    @Override
    public Optional<Adress> createAdress(int matNr, Adress adress) {
        try {
            Student student = em.find(Student.class, matNr);
            if (student.getAdress() != null) {
                log.error("Student besitzt bereits eine Adresse");
                return Optional.ofNullable(null);
            }
            student.setAdress(adress);
            em.persist(student);
            return Optional.ofNullable(adress);
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            log.error("Eine Exception wurde geworfen \n" + e.toString());
            return Optional.ofNullable(null);
        }
    }

    @Override
    public Optional<Adress> getAdress(int matNr) throws EntityNotFoundException {
        try {
            Student student = em.find(Student.class, matNr);
            if (student == null) {
                log.error("Student konnte nicht gefunden werden");
                throw new EntityNotFoundException(Student.class, matNr);
            }
            if (student.getAdress() == null) {
                log.error("Es wurde keine Adresse für den Studenten gefunden");
                throw new EntityNotFoundException(Adress.class, matNr);
            }
            return Optional.ofNullable(student.getAdress());
        } catch (EntityExistsException | TransactionRequiredException | IllegalArgumentException e) {
            log.error("Eine Exception wurde geworfen \n" + e.toString());
            return Optional.ofNullable(null);
        }
    }

    @Override
    public boolean deleteAdress(int matNr) throws EntityNotFoundException {
        try {
            Student student = em.find(Student.class, matNr);
            if (student.getAdress() == null) {
                log.error("Es wurde keine Adresse für den Studenten gefunden");
                throw new EntityNotFoundException(Adress.class, matNr);
            }
            student.setAdress(null);
            em.persist(student);
            return true;
        } catch (EntityExistsException | TransactionRequiredException | IllegalArgumentException e) {
            log.error("Eine Exception wurde geworfen \n" + e.toString());
            return false;
        }
    }

    @Override
    public Optional<Adress> changeAdress(int matNr, Adress adress) throws EntityNotFoundException {
        try {
            Student student = em.find(Student.class, matNr);
            if (student.getAdress() == null) {
                log.error("Es wurde keine Adresse für den Studenten gefunden");
                throw new EntityNotFoundException(Adress.class, matNr);
            }
            student.setAdress(adress);
            em.persist(student);
            return Optional.ofNullable(adress);
        } catch (EntityExistsException | TransactionRequiredException | IllegalArgumentException e) {
            log.error("Eine Exception wurde geworfen \n" + e.toString());
            return Optional.ofNullable(null);
        }
    }
}
