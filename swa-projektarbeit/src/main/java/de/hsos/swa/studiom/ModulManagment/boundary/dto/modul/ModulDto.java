/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-02-03 20:18:09
 * @modify date 2022-02-03 20:18:09
 * @desc [description]
 */
package de.hsos.swa.studiom.ModulManagment.boundary.dto.modul;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import de.hsos.swa.studiom.ModulManagment.boundary.dto.question.QuestionDto;
import de.hsos.swa.studiom.ModulManagment.entity.Modul;
import de.hsos.swa.studiom.ModulManagment.entity.Question;
import de.hsos.swa.studiom.StudentsManagement.boundary.dto.Student.StudentDTO;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;

public class ModulDto {

    public int id;

    public String name;

    public String description;

    public boolean isProject;

    @JsonInclude(Include.NON_NULL)
    public Integer studentenAnzahl;

    public List<StudentDTO> students;

    public List<QuestionDto> questions;

    public ModulDto() {
    }

    public ModulDto(int id, String name, boolean isProject) {
        this.id = id;
        this.name = name;
        this.isProject = isProject;
    }

    public ModulDto(int modulID, String name, String description, boolean isProject, Integer studentenAnzahl) {
        this.id = modulID;
        this.name = name;
        this.description = description;
        this.isProject = isProject;
        this.studentenAnzahl = studentenAnzahl;
    }

    public ModulDto(int id, String name, boolean isProject, Integer studentenAnzahl) {
        this.id = id;
        this.name = name;
        this.isProject = isProject;
        this.studentenAnzahl = studentenAnzahl;
    }

    public ModulDto(int id, String name, String description, boolean isProject, Integer studentenAnzahl,
            List<StudentDTO> students, List<QuestionDto> questions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isProject = isProject;
        this.studentenAnzahl = studentenAnzahl;
        this.students = students;
        this.questions = questions;
    }

    public static class Converter {

        public static ModulDto toSimpleModuleDTO(Modul module) {
            return new ModulDto(module.getModulID(), module.getName(),
                    module.getIsProject(), module.studentenAnzahl());
        }

        public static ModulDto toModuleDTO(Modul module) {
            List<StudentDTO> students = module.getStudenten().stream().map(Converter::StudentToDto)
                    .collect(Collectors.toList());
            List<QuestionDto> questions = module.getQuestions().stream().map(QuestionDto.Converter::SimpleDto)
                    .collect(Collectors.toList());
            return new ModulDto(module.getModulID(), module.getName(), module.getDescription(),
                    module.getIsProject(), module.studentenAnzahl(), students, questions);
        }

        public static ModulDto toMinimalModuleDTO(Modul module) {
            return new ModulDto(module.getModulID(), module.getName(), module.getIsProject());
        }

        public static ModulDto toMinimalHTTPModuleDTO(Modul module) {
            return new ModulDto(module.getModulID(), module.getName(), module.getDescription(),
                    module.getIsProject(), module.studentenAnzahl());
        }

        public static StudentDTO StudentToDto(Student student) {
            StudentDTO uDto = new StudentDTO();
            uDto.setMatNr(student.getMatNr());
            return uDto;
        }

        public static ModulDto toDetailHTTPModule(Modul module) {
            List<StudentDTO> students = module.getStudenten().stream().map(StudentDTO.Converter::toHTTPStudentDTO)
                    .collect(Collectors.toList());
            List<QuestionDto> questions = module.getQuestions().stream()
                    .map(QuestionDto.Converter::QuestionToDtoAnswers).collect(Collectors.toList());

            return new ModulDto(module.getModulID(), module.getName(), module.getDescription(),
                    module.isProject(), module.studentenAnzahl(), students, questions);
        }
    }

}
