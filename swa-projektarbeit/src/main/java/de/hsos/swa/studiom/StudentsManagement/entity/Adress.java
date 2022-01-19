package de.hsos.swa.studiom.StudentsManagement.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * @author Joana Wegener
 */

 @Entity
public class Adress {
    @Id
    @SequenceGenerator(name = "AdrIdSequence", sequenceName = "adress_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "AdrIdSequence")
    private int id;
    private String street;
    private int nr;
    private int zipCode;
    private String town;

    
    public Adress() {
    }


    public Adress(String street, int nr, int zipCode, String town) {
        this.street = street;
        this.nr = nr;
        this.zipCode = zipCode;
        this.town = town;
    }
}
