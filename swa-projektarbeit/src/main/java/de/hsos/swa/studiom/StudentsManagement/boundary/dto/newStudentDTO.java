/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 14:20:14
 * @modify date 2022-01-22 14:20:14
 * @desc [description]
 */

 package de.hsos.swa.studiom.StudentsManagement.boundary.dto;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;


public class newStudentDTO {
    public String name;
    public String email;

    public newStudentDTO() {
    }

    public static class Converter{
        public static Student toStudent(newStudentDTO studentDTO){
            return new Student(studentDTO.name, studentDTO.email);
        }
        
        public static newStudentDTO toDTO(Student student){
            newStudentDTO studentDTO  = new newStudentDTO();
            studentDTO.name = student.getName();
            studentDTO.email = student.getEmail();
            return studentDTO;
        }
    }
}
