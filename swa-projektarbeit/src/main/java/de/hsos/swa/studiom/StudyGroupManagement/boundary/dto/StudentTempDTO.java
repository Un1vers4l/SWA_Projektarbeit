/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-29 07:45:02
 * @modify date 2022-01-29 07:45:02
 * @desc [description]
 */
package de.hsos.swa.studiom.StudyGroupManagement.boundary.dto;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;

public class StudentTempDTO {
    public String name;
    public int matNr;

    public StudentTempDTO() {
    }

    public static class Converter{
        public static StudentTempDTO toDTO(Student student){
            StudentTempDTO studentDTO  = new StudentTempDTO();
            studentDTO.name = student.getFullName();
            studentDTO.matNr = student.getMatNr();
            return studentDTO;
        }
    }
}
