/**
 * @author Joana Wegener (855518)
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 19:20:03
 * @modify date 2022-02-12 10:39:02
 * @desc [description]
 */
package de.hsos.swa.studiom.StudentsManagement.boundary.dto.Student;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import de.hsos.swa.studiom.ModulManagment.boundary.dto.answer.AnswerDto;
import de.hsos.swa.studiom.ModulManagment.boundary.dto.modul.ModulDto;
import de.hsos.swa.studiom.ModulManagment.boundary.dto.question.QuestionDto;
import de.hsos.swa.studiom.StudentsManagement.boundary.dto.Adresse.AdressDTO;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudyGroupManagement.boundary.dto.GroupDTO;
import de.hsos.swa.studiom.UserManagement.boundary.dto.UserDto;

@JsonInclude(Include.NON_NULL)
public class StudentDTO {
    public int matNr;
    public String name;
    public String email;
    public List<ModulDto> modules;
    public List<GroupDTO> groups;
    public AdressDTO adress;
    public List<GroupDTO> myGroups;
    public List<QuestionDto> myQuestion;
    public List<AnswerDto> myAnswer;
    public UserDto user;

    public StudentDTO() {
    }

    public StudentDTO(int matNr, String name, String email, List<ModulDto> modules, List<GroupDTO> groups,
            AdressDTO adress) {
        this.matNr = matNr;
        this.name = name;
        this.email = email;
        this.modules = modules;
        this.groups = groups;
        this.adress = adress;
    }

    public StudentDTO(int matNr, String name, String email, AdressDTO adress, UserDto user) {
        this.matNr = matNr;
        this.name = name;
        this.email = email;
        this.adress = adress;
        this.user = user;
    }

    public StudentDTO(int matNr, String name, String email, List<ModulDto> modules, List<GroupDTO> groups,
            List<QuestionDto> myQuestion, List<AnswerDto> myAnswer) {
        this.matNr = matNr;
        this.name = name;
        this.email = email;
        this.modules = modules;
        this.groups = groups;
        this.myQuestion = myQuestion;
        this.myAnswer = myAnswer;
    }

    public int getMatNr() {
        return this.matNr;
    }

    public void setMatNr(int matNr) {
        this.matNr = matNr;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ModulDto> getModules() {
        return this.modules;
    }

    public void setModules(List<ModulDto> modules) {
        this.modules = modules;
    }

    public List<GroupDTO> getGroups() {
        return this.groups;
    }

    public void setGroups(List<GroupDTO> groups) {
        this.groups = groups;
    }

    public AdressDTO getAdress() {
        return this.adress;
    }

    public void setAdress(AdressDTO adress) {
        this.adress = adress;
    }

    public List<GroupDTO> getMyGroups() {
        return myGroups;
    }

    public void setMyGroups(List<GroupDTO> myGroups) {
        this.myGroups = myGroups;
    }

    public List<QuestionDto> getMyQuestion() {
        return myQuestion;
    }

    public void setMyQuestion(List<QuestionDto> myQuestion) {
        this.myQuestion = myQuestion;
    }

    public List<AnswerDto> getMyAnswer() {
        return myAnswer;
    }

    public void setMyAnswer(List<AnswerDto> myAnswer) {
        this.myAnswer = myAnswer;
    }

    public StudentDTO(int matNr, String name, String email) {
        this.matNr = matNr;
        this.name = name;
        this.email = email;
    }

    public static class Converter {
        public static StudentDTO toSimpleStudentDTO(Student student) {
            List<ModulDto> modules = student.getModules().stream().map(ModulDto.Converter::toMinimalModuleDTO)
                    .collect(Collectors.toList());
            List<GroupDTO> groups = student.getGroups().stream().map(GroupDTO.Converter::toMinimalGroupDTO)
                    .collect(Collectors.toList());

            return new StudentDTO(student.getMatNr(), student.getFullName(), student.getEmail(), modules,
                    groups, AdressDTO.Converter.toDto(student.getAdress()));
        }

        public static StudentDTO toUserSimpleStudentDTO(Student student) {
            return new StudentDTO(student.getMatNr(), student.getFullName(), student.getEmail(),
                    AdressDTO.Converter.toDto(student.getAdress()), UserDto.Converter.SimpleDto(student.getUser()));
        }

        public static StudentDTO toMinimalStudentDTO(Student student) {
            return new StudentDTO(student.getMatNr(), student.getFullName(), student.getEmail());
        }

        public static StudentDTO toHTTPStudentDTO(Student student) {
            List<ModulDto> modules = student.getModules().stream().map(ModulDto.Converter::toMinimalHTTPModuleDTO)
                    .collect(Collectors.toList());
            List<GroupDTO> groups = student.getGroups().stream().map(GroupDTO.Converter::toHTTPGroupDTO)
                    .collect(Collectors.toList());
            List<QuestionDto> questions = student.getMyQuestion().stream().map(QuestionDto.Converter::QuestionToDto)
                    .collect(Collectors.toList());
            List<AnswerDto> answers = student.getMyAnswer().stream().map(AnswerDto.Converter::AnswerToDto)
                    .collect(Collectors.toList());

            return new StudentDTO(student.getMatNr(), student.getFullName(), student.getEmail(), modules,
                    groups, questions, answers);
        }

    }
}
