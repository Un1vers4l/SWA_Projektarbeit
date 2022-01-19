package de.hsos.swa.studiom.StudentsManagement.entity;

/**
 * @author Joana Wegener
 */

public class Adress {
    private String street;
    private int nr;
    private int zipCode;
    private String town;

    public Adress(String street, int nr, int zipCode, String town) {
        this.street = street;
        this.nr = nr;
        this.zipCode = zipCode;
        this.town = town;
    }
}
