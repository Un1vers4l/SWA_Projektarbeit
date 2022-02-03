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
    public Module getModul(int modulId){
        return em.find(Module.class, modulId);
    }
    @Override
    public List<Module> getAllModul(){
        TypedQuery<Module> query = em.createNamedQuery("Module.findAll", Module.class);
        return query.getResultList();
    }
    @Override
    public Module createdModule(String name, String description, boolean isProject){
        return null;
    }
    @Override
    public Module changeModule(int modulId, Module changes) throws EntityNotFoundException{
        Module module = this.getModul(modulId);
        if(module == null){
            log.warn("Module mit der ID "+ modulId + "konnte nicht gefunden");
            throw new EntityNotFoundException(Module.class, modulId);
        }
        module.changeMyData(changes);
        return module;
    }
    @Override
    public boolean deleteModule(int modulId){
        Module module = this.getModul(modulId);
        if (module == null) return false;

        em.remove(module);

        log.info("Remove Mdoule: " + modulId);
        log.debug("Remove("+ module.toString() +')');
        return true;
    }
    @Override
    public boolean addStudentToModule(int matNr) throws EntityNotFoundException{
        Optional<Student> student = studentService.getStudent(matNr);
        return false;
    }
}
