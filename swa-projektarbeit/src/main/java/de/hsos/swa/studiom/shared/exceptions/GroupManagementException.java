package de.hsos.swa.studiom.shared.exceptions;

import de.hsos.swa.studiom.StudyGroupManagement.entity.GroupType;

public class GroupManagementException extends Exception {
    private final String message;

    public GroupManagementException(GroupType type) {
        this.message = String.format("Only the owner can manage the %s", type.toString().toLowerCase());
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "GroupManagementException: " + message;
    }
}
