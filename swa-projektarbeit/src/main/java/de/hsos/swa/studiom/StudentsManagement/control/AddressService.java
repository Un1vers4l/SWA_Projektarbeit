/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-31 13:10:30
 * @modify date 2022-01-31 13:10:30
 * @desc [description]
 */
package de.hsos.swa.studiom.StudentsManagement.control;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import de.hsos.swa.studiom.StudentsManagement.entity.Adress;



public interface AddressService {
    public Optional<Adress> createAdress(int matNr, Adress adress);

    public Optional<Adress> getAdress(int matNr);

    public boolean deleteAdress(int matNr);

    public Optional<Adress> changeAdress(int matNr, Adress adress);
}
