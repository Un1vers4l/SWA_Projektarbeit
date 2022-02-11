/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 14:20:14
 * @modify date 2022-01-22 14:20:14
 * @desc [description]
 */

 package de.hsos.swa.studiom.StudentsManagement.boundary.dto.Student;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;


public class newStudentDTO {
    private String vorname;
    private String nachname;
    public String email;

    public newStudentDTO() {
    }

    public newStudentDTO(String vorname, String nachname, String email) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
    }

    public String getVorname() {
        return this.vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return this.nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public static class Converter{
        public static Student toStudent(newStudentDTO studentDTO){
            return new Student(studentDTO.vorname, studentDTO.nachname, studentDTO.email);
        }
        
        public static newStudentDTO toDTO(Student student){
            newStudentDTO studentDTO  = new newStudentDTO();
            studentDTO.vorname = student.getVorname();
            studentDTO.nachname = student.getNachname();
            studentDTO.email = student.getEmail();
            return studentDTO;
        }
    }
}
