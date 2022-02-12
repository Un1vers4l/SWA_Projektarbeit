package de.hsos.swa.studiom.StudentsManagement.boundary.dto.Student;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;

public class PostStudentDTO {
    public String vorname;
    public String nachname;
    public String password;

    public PostStudentDTO(String vorname, String nachname) {
        this.vorname = vorname;
        this.nachname = nachname;
    }
    

    public PostStudentDTO() {
    }


    public static class Converter {

        public static PostStudentDTO toPutStudentDTO(Student student) {
            return new PostStudentDTO(student.getVorname(), student.getNachname());
        }
    }
}
