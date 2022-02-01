/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-01-31 15:11:14
 * @modify date 2022-01-31 15:11:14
 * @desc [description]
 */
package de.hsos.swa.studiom.shared.exception;

public class UsernameExistExeption extends Exception {
    private String message = "User mit diesem Username Existiert bereits";

    public UsernameExistExeption() {
     
    }

    @Override
    public String toString() { return message; }

    @Override
    public String getMessage() { return this.toString();}
    
}
