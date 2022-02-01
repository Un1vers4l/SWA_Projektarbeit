/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-31 13:10:30
 * @modify date 2022-02-01 14:39:25
 * @desc [description]
 */
package de.hsos.swa.studiom.StudentsManagement.control;

import java.util.Optional;

import de.hsos.swa.studiom.StudentsManagement.entity.Adress;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;



public interface AddressService {
    public Optional<Adress> createAdress(int matNr, Adress adress);

    public Optional<Adress> getAdress(int matNr) throws EntityNotFoundException;

    public boolean deleteAdress(int matNr) throws EntityNotFoundException;

    public Optional<Adress> changeAdress(int matNr, Adress adress) throws EntityNotFoundException;
}
