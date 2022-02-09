/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-02-02 22:08:42
 * @modify date 2022-02-02 22:08:42
 * @desc [description]
 */
package de.hsos.swa.studiom.ModulManagment.gateway;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.jboss.logging.Logger;

import de.hsos.swa.studiom.ModulManagment.control.ModulService;
import de.hsos.swa.studiom.ModulManagment.entity.Modul;
import de.hsos.swa.studiom.StudentsManagement.control.StudentService;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudyGroupManagement.entity.Group;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;

@RequestScoped
@Transactional
public class ModulRepository implements ModulService{

    @Inject
    StudentService studentService;

    @Inject
    EntityManager em;

    Logger log = Logger.getLogger(ModulRepository.class);
    
    /** 
     * @param modulId
     * @return Optional<Modul>
     */
    @Override
    public Optional<Modul> getModul(int modulId){
        return Optional.ofNullable(em.find(Modul.class, modulId));
    }
    
    /** 
     * @return List<Modul>
     */
    @Override
    public List<Modul> getAllModul(){
        TypedQuery<Modul> query = em.createNamedQuery("Modul.findAll", Modul.class);
        return query.getResultList();
    }
    
    /** 
     * @param name
     * @param description
     * @param isProject
     * @return Modul
     */
    @Override
    public Modul createdModule(String name, String description, boolean isProject){
        if(name == null || description == null) throw new IllegalArgumentException();
        Modul modul = new Modul(name, description, isProject);
        
        em.persist(modul);
        log.info("Modul(Modul: "+ modul.getModulID() + ") wurde erzeugt");
        return modul;
    }
    
    /** 
     * @param modulId
     * @param changes
     * @return Modul
     * @throws EntityNotFoundException
     */
    @Override
    public Modul changeModule(int modulId, Modul changes) throws EntityNotFoundException{
        if(changes == null) throw new IllegalArgumentException();

        Modul modul = this.getModulWithExeption(modulId);

        modul.changeMyData(changes);
        return modul;
    }

    
    /** 
     * @param modulId
     * @return boolean gibt false zurueck falls es kein Modul mit der ID gibt 
     */
    @Override
    public boolean deleteModule(int modulId){
        Optional<Modul> modulOpt = this.getModul(modulId);
        if (!modulOpt.isPresent()) return false;
        Modul modul = modulOpt.get();

        for(Group projekt : modul.getProjects()){
            for (Student student: projekt.getStudents()){
                student.removeGroup(projekt);
            }
        }

        for(Student student : modul.getStudenten()){
            student.removeModule(modul);
        }

        em.remove(modul);

        log.info("Remove Modul: " + modulId);
        log.debug("Remove("+ modul.toString() +')');
        return true;
    }
    
    /** 
     * @param modulId
     * @param matNr
     * @return boolean
     * @throws EntityNotFoundException
     */
    @Override
    public boolean addStudentFromModule(int modulId, int matNr) throws EntityNotFoundException{
        Optional<Student> student = studentService.getStudent(matNr);

        Modul modul = this.getModulWithExeption(modulId);
        boolean success = student.get().addModule(modul);
        //ist doof aber damit es nicht persitiert wird
        if(!success) return success;

        em.persist(student.get());
        return success;
    }
    
    /** 
     * @param modulId
     * @param matNr
     * @return boolean
     * @throws EntityNotFoundException
     */
    @Override
    public boolean removeStudentFromModule(int modulId, int matNr) throws EntityNotFoundException {
        Optional<Student> student = studentService.getStudent(matNr);

        Modul modul = this.getModulWithExeption(modulId);

        boolean success = student.get().removeModule(modul);
        if(!success) return success;

        em.persist(student.get());
        return success;
    }
    
    /** 
     * @param modulId
     * @return Modul
     * @throws EntityNotFoundException
     */
    @Override
    public Modul getModulWithExeption(int modulId) throws EntityNotFoundException{
        Optional<Modul> modul = this.getModul(modulId);
        if(!modul.isPresent()){
            log.warn("Modul mit der ID "+ modulId + "konnte nicht gefunden");
            throw new EntityNotFoundException(Modul.class, modulId);
        }
        return modul.get();
    }
}
