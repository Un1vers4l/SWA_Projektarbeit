package de.hsos.swa.studiom.ModuleManagment.control;

import java.util.List;
import java.util.Optional;

import de.hsos.swa.studiom.ModuleManagment.entity.Module;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;

public interface ModuleService {
    public Optional<Module> getModul(int modulId);
    public List<Module> getAllModul();
    public Module createdModule(String name, String description, boolean isProject);
    public Module changeModule(int modulId, Module module) throws EntityNotFoundException;
    public boolean deleteModule(int modulId);
    public boolean addStudentToModule(int modulId, int matNr) throws EntityNotFoundException;
    public boolean removeStudentToModule(int modulId, int matNr) throws EntityNotFoundException;
    public Module getModuleWithExeption(int modulId) throws EntityNotFoundException;
}
