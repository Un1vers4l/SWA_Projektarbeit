/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-02-05 20:12:53
 * @modify date 2022-02-05 20:12:53
 * @desc [description]
 */
package de.hsos.swa.studiom.StudentsManagement.boundary.dto;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;

public class HTTPStudentMin {
    public String name;
    public String email;
    public int matNr;

    public HTTPStudentMin() {
    }

    public static class Converter {
        public static HTTPStudentMin toDTO(Student student){
            HTTPStudentMin studentDTO  = new HTTPStudentMin();
            studentDTO.name = student.getName();
            studentDTO.email = student.getEmail();
            studentDTO.matNr= student.getMatNr();
            return studentDTO;
        }
    }
}
