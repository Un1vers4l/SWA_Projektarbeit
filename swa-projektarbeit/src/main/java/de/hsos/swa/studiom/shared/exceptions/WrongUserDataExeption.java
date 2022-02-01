/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-01-31 15:11:17
 * @modify date 2022-01-31 15:11:17
 * @desc [description]
 */
package de.hsos.swa.studiom.shared.exceptions;

public class WrongUserDataExeption extends Exception {
    private String message = "Username oder Passoword ist falsch";

    public WrongUserDataExeption() {
     
    }

    @Override
    public String toString() { return message; }

    @Override
    public String getMessage() { return this.toString();}
    
}
