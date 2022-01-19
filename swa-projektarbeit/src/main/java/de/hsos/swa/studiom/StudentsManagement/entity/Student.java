package de.hsos.swa.studiom.StudentsManagement.entity;

import java.util.Set;

import de.hsos.swa.studiom.shared.mock.MockGroup;
import de.hsos.swa.studiom.shared.mock.MockModule;

/**
 * @author Joana Wegener
 */

public class Student {
    private int matNr;
    private String name;
    private String email;
    private Set<MockModule> modules;
    private Set<MockGroup> groups;
    private Adress adress;

    public void addAdress(String street, int nr, int zipCode, String town) {
        this.adress = new Adress(street, nr, zipCode, town);
    }

    public Student(String name) {
        this.name = name;
    }
}
