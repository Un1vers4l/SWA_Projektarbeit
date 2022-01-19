package de.hsos.swa.studiom.StudentsManagement.boundary.dto;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;

public class StudentDTO {
    public String name;
    public String email;

    public StudentDTO() {
    }

    public static class Converter{
        public static Student dtoToStudent(StudentDTO studentDTO){
            return new Student(studentDTO.name, studentDTO.email);
        }
        public static StudentDTO studentToDTO(Student student){
            StudentDTO studentDTO  = new StudentDTO();
            studentDTO.name = student.getName();
            studentDTO.email = student.getEmail();
            return studentDTO;
        }
    }
}
