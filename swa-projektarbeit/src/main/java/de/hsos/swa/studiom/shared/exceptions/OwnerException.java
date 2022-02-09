/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-02-09 19:21:07
 * @modify date 2022-02-09 19:21:07
 * @desc [description]
 */
package de.hsos.swa.studiom.shared.exceptions;

import de.hsos.swa.studiom.StudyGroupManagement.entity.GroupType;

public class OwnerException extends Exception {
    private final String message;

    public OwnerException(int matNr, GroupType type, int groupId, String action) {
        this.message = String.format("Student with MatNr: %s is the owner of the %s with Id %s and can not %s",
                String.valueOf(matNr),
                type.toString().toLowerCase(), String.valueOf(groupId), action);
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
