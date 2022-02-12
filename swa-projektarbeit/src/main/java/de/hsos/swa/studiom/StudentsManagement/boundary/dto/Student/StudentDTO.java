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

import de.hsos.swa.studiom.ModulManagment.boundary.dto.answer.AnswerDto;
import de.hsos.swa.studiom.ModulManagment.boundary.dto.modul.ModulDto;
import de.hsos.swa.studiom.StudentsManagement.boundary.dto.Adresse.AdressDTO;
import de.hsos.swa.studiom.StudentsManagement.boundary.dto.Group.GroupDTO;
import de.hsos.swa.studiom.StudentsManagement.boundary.dto.Question.QuestionDTO;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;

public class StudentDTO {
    public int matNr;
    public String name;
    public String email;
    public List<ModulDto> modules = null;
    public List<GroupDTO> groups = null;
    public AdressDTO adress;
    private Set<GroupDTO> myGroups;
    private Set<QuestionDTO> myQuestion;
    private Set<AnswerDto> myAnswer;

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

    public Set<GroupDTO> getMyGroups() {
        return myGroups;
    }

    public void setMyGroups(Set<GroupDTO> myGroups) {
        this.myGroups = myGroups;
    }

    public Set<QuestionDTO> getMyQuestion() {
        return myQuestion;
    }

    public void setMyQuestion(Set<QuestionDTO> myQuestion) {
        this.myQuestion = myQuestion;
    }

    public Set<AnswerDto> getMyAnswer() {
        return myAnswer;
    }

    public void setMyAnswer(Set<AnswerDto> myAnswer) {
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
            List<GroupDTO> groups = student.getGroups().stream().map(GroupDTO.Converter::toSimpleGroupDTO)
                    .collect(Collectors.toList());

            return new StudentDTO(student.getMatNr(), student.getFullName(), student.getEmail(), modules,
                    groups, AdressDTO.Converter.toDto(student.getAdress()));
        }

        public static StudentDTO toMinimalStudentDTO(Student student) {
            return new StudentDTO(student.getMatNr(), student.getFullName(), student.getEmail());
        }

    }
}
