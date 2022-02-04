/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-02-03 20:17:48
 * @modify date 2022-02-03 20:17:48
 * @desc [description]
 */
package de.hsos.swa.studiom.ModulManagment.control;

import java.util.List;
import java.util.Optional;

import de.hsos.swa.studiom.ModulManagment.entity.Modul;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;

public interface ModulService {
    public Optional<Modul> getModul(int modulId);
    public List<Modul> getAllModul();
    public Modul createdModule(String name, String description, boolean isProject);
    public Modul changeModule(int modulId, Modul modul) throws EntityNotFoundException;
    public boolean deleteModule(int modulId);
    public boolean addStudentFromModule(int modulId, int matNr) throws EntityNotFoundException;
    public boolean removeStudentFromModule(int modulId, int matNr) throws EntityNotFoundException;
    public Modul getModulWithExeption(int modulId) throws EntityNotFoundException;
}
