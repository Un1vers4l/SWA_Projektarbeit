/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-02-02 22:08:42
 * @modify date 2022-02-02 22:08:42
 * @desc [description]
 */
package de.hsos.swa.studiom.ModuleManagment.gateway;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.jboss.logging.Logger;

import de.hsos.swa.studiom.ModuleManagment.control.ModuleService;
import de.hsos.swa.studiom.ModuleManagment.entity.Module;
import de.hsos.swa.studiom.StudentsManagement.control.StudentService;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;

@RequestScoped
@Transactional
public class ModuleRepository implements ModuleService{
    @Inject
    StudentService studentService;
    @Inject
    EntityManager em;

    Logger log = Logger.getLogger(ModuleRepository.class);

    @Override
    public Optional<Module> getModul(int modulId){
        return Optional.ofNullable(em.find(Module.class, modulId));
    }
    @Override
    public List<Module> getAllModul(){
        TypedQuery<Module> query = em.createNamedQuery("Module.findAll", Module.class);
        return query.getResultList();
    }
    @Override
    public Module createdModule(String name, String description, boolean isProject){
        if(name == null || description == null) throw new IllegalArgumentException();
        Module module = new Module(name, description, isProject);
        
        em.persist(module);
        log.info("Mdoule(module: "+ module.getModuleID() + ") wurde erzeugt");
        return module;
    }
    @Override
    public Module changeModule(int modulId, Module changes) throws EntityNotFoundException{
        if(changes == null) throw new IllegalArgumentException();

        Module module = this.getModuleWithExeption(modulId);

        module.changeMyData(changes);
        return module;
    }

    @Override
    public boolean deleteModule(int modulId){
        Optional<Module> module = this.getModul(modulId);
        if (!module.isPresent()) return false;

        em.remove(module);

        log.info("Remove Mdoule: " + modulId);
        log.debug("Remove("+ module.toString() +')');
        return true;
    }
    @Override
    public boolean addStudentToModule(int modulId, int matNr) throws EntityNotFoundException{
        Optional<Student> student = studentService.getStudent(matNr);

        Module module = this.getModuleWithExeption(modulId);
    
        return student.get().addModule(module);
    }
    @Override
    public boolean removeStudentToModule(int modulId, int matNr) throws EntityNotFoundException {
        Optional<Student> student = studentService.getStudent(matNr);

        Module module = this.getModuleWithExeption(modulId);

        return student.get().removeModule(module);
    }
    @Override
    public Module getModuleWithExeption(int modulId) throws EntityNotFoundException{
        Optional<Module> module = this.getModul(modulId);
        if(!module.isPresent()){
            log.warn("Module mit der ID "+ modulId + "konnte nicht gefunden");
            throw new EntityNotFoundException(Module.class, modulId);
        }
        return module.get();
    }
}
