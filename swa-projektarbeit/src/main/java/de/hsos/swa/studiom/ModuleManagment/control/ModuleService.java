package de.hsos.swa.studiom.ModuleManagment.control;

import java.util.List;

import de.hsos.swa.studiom.ModuleManagment.entity.Module;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;

public interface ModuleService {
    public Module getModul(int modulId);
    public List<Module> getAllModul();
    public Module createdModule(String name, String description, boolean isProject);
    public Module changeModule(int modulId, Module module) throws EntityNotFoundException;
    public boolean deleteModule(int modulId);
    public boolean addStudentToModule(int matNr) throws EntityNotFoundException;
}
