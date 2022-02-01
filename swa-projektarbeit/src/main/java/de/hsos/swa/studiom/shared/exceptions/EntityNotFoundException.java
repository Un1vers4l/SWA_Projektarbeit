/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-02-01 09:45:16
 * @modify date 2022-02-01 09:45:16
 * @desc [description]
 */
package de.hsos.swa.studiom.shared.exceptions;

public class EntityNotFoundException extends Exception {
    private final String message;

    public EntityNotFoundException(Class<?> c, int id) {
        this.message = String.format("Entity with id %s of Type %s could not be found", id, c.getSimpleName());
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "EntityNotFound: " + message;
    }

}
