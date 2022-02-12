/**
 * @author Joana Wegener (855518)
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 14:20:14
 * @modify date 2022-01-22 14:20:14
 * @desc [description]
 */

package de.hsos.swa.studiom.StudentsManagement.boundary.dto.Student;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;

public class PutStudentDTO {
    public int matNr;
    public String vorname;
    public String nachname;
    public String email;

    public PutStudentDTO() {
    }
    

    public PutStudentDTO(int matNr, String vorname, String nachname, String email) {
        this.matNr = matNr;
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
    }

    public static class Converter {
        public static Student toStudent(PutStudentDTO studentDTO) {
            return new Student(studentDTO.vorname, studentDTO.nachname, studentDTO.email);
        }
    }
}
