/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-02-01 11:32:21
 * @modify date 2022-02-01 11:32:21
 * @desc [description]
 */
package de.hsos.swa.studiom.shared.exceptions;

public class StudentIsNotStudentExeption extends Exception {
    private String message = "Student mit der Role Student hat keinen verweis auf ein Student";

    public StudentIsNotStudentExeption() {
     
    }

    @Override
    public String toString() { return message; }

    @Override
    public String getMessage() { return this.toString();}
}
