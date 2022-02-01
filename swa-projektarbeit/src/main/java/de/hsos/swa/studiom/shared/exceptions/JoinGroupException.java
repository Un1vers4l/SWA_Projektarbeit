package de.hsos.swa.studiom.shared.exceptions;

import de.hsos.swa.studiom.StudyGroupManagement.entity.GroupType;

public class JoinGroupException extends Exception {
    private final String message;

    public JoinGroupException(GroupType type, int groupId, int matNr, String reason) {
        this.message = String.format("Student with MatNr: %s could not join %s with ID %s: %s", String.valueOf(matNr),
                type.toString().toLowerCase(), String.valueOf(groupId), reason);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "JoinGroupException: " + message;
    }
}
