package de.hsos.swa.studiom.ModulManagment.boundary.http.dto;

import java.util.ArrayList;
import java.util.List;

import de.hsos.swa.studiom.ModulManagment.boundary.dto.question.QuestionDto;
import de.hsos.swa.studiom.ModulManagment.entity.Modul;
import de.hsos.swa.studiom.ModulManagment.entity.Question;
import de.hsos.swa.studiom.StudentsManagement.boundary.dto.Student.StudentDTO;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;

public class HTTPModulDTO {
    public int modulID;

    public String name;

    public String description;

    public boolean isProject;

    public Integer studentenAnzahl;

    public List<StudentDTO> students;

    public List<QuestionDto> questions;

    public HTTPModulDTO(int modulID, String name, String description, boolean isProject, Integer studentenAnzahl,
            List<StudentDTO> students, List<QuestionDto> questions) {
        this.modulID = modulID;
        this.name = name;
        this.description = description;
        this.isProject = isProject;
        this.studentenAnzahl = studentenAnzahl;
        this.students = students;
        this.questions = questions;
    }

    public HTTPModulDTO() {
    }

    public static class Converter {

        public static HTTPModulDTO toDto(Modul modul) {
            List<StudentDTO> studentDTOs = new ArrayList<>();
            for (Student stud : modul.getStudenten()) {
                studentDTOs.add(StudentDTO.Converter.toHTTPStudentDTO(stud));
            }
            List<QuestionDto> questionDtos = new ArrayList<>();
            for (Question question : modul.getQuestions()) {
                questionDtos.add(QuestionDto.Converter.QuestionToDtoAnswers(question));
            }
            return new HTTPModulDTO(modul.getModulID(), modul.getName(), modul.getDescription(),
                    modul.isProject(), modul.studentenAnzahl(), studentDTOs, questionDtos);
        }
    }
}
