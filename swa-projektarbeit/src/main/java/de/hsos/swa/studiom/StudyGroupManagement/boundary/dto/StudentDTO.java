/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-29 07:45:02
 * @modify date 2022-01-29 07:45:02
 * @desc [description]
 */
package de.hsos.swa.studiom.StudyGroupManagement.boundary.dto;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;

public class StudentDTO {
    public String name;
    public int matNr;

    public StudentDTO() {
    }

    public static class Converter{
        public static StudentDTO toDTO(Student student){
            StudentDTO studentDTO  = new StudentDTO();
            studentDTO.name = student.getFullName();
            studentDTO.matNr = student.getMatNr();
            return studentDTO;
        }
    }
}
