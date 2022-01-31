/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-01-31 15:11:16
 * @modify date 2022-01-31 15:11:16
 * @desc [description]
 */
package de.hsos.swa.studiom.UserManagement.exception;

public class UserNotExistExeption extends Exception {
    private String message = "Es gibt kein User mit der UserID";

    public UserNotExistExeption() {
     
    }

    @Override
    public String toString() { return message; }

    @Override
    public String getMessage() { return this.toString();}
}
