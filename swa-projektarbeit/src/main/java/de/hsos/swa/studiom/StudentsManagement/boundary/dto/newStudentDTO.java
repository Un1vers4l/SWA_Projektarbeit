package de.hsos.swa.studiom.StudentsManagement.boundary.dto;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;

public class newStudentDTO {
    public String name;
    public String email;

    public newStudentDTO() {
    }

    public static class Converter{
        public static Student dtoToStudent(newStudentDTO studentDTO){
            return new Student(studentDTO.name, studentDTO.email);
        }
        public static newStudentDTO studentToDTO(Student student){
            newStudentDTO studentDTO  = new newStudentDTO();
            studentDTO.name = student.getName();
            studentDTO.email = student.getEmail();
            return studentDTO;
        }
    }
}
