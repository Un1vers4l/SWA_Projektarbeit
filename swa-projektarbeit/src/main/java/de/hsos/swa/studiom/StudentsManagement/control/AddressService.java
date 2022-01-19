package de.hsos.swa.studiom.StudentsManagement.control;

import java.util.Optional;

import de.hsos.swa.studiom.StudentsManagement.entity.Adress;

/**
 * @author Joana Wegener
 */

public interface AddressService {
    public Optional<Adress> createAdress(int matNr, String street, int zipCode, String town);

    public Optional<Adress> getAdress(int matNr);

    public boolean deleteAdress(int matNr);

    public Optional<Adress> changeAdress(int matNr, String street, int zipCode, String town);
}
