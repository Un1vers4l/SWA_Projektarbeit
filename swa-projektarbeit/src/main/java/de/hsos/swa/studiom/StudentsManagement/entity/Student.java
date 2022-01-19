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
}
