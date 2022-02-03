/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-02-03 10:16:50
 * @modify date 2022-02-03 10:16:50
 * @desc [description]
 */
package de.hsos.swa.studiom.shared.exceptions;

public class CanNotGeneratUserExeption extends Exception {
    private String message = "Der User konnte nicht erzeugt werden";

    public CanNotGeneratUserExeption() {
     
    }

    @Override
    public String toString() { return message; }

    @Override
    public String getMessage() { return this.toString();}
}
