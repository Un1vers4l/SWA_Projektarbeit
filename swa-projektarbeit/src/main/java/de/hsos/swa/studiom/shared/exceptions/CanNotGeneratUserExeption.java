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
