/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 19:08:21
 * @modify date 2022-01-22 19:08:21
 * @desc [description]
 */

package de.hsos.swa.studiom.StudentsManagement.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Adress {
    @Id
    @SequenceGenerator(name = "AdrIdSequence", sequenceName = "adress_seq", allocationSize = 1, initialValue = 5)
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

}
