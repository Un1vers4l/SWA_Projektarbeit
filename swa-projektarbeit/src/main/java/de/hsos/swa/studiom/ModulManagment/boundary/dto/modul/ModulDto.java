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

import de.hsos.swa.studiom.ModulManagment.entity.Modul;
import de.hsos.swa.studiom.StudentsManagement.boundary.dto.StudentDTO;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;

public class ModulDto {

    private int modulID;

    private String name;

    private String description;

    private boolean isProject;
    
    private Integer studentenAnzahl;

    private List<StudentDTO> students;

    public ModulDto(int modulID, String name, String description, boolean isProject, int studentenAnzahl, List<StudentDTO> students) {
        this.modulID = modulID;
        this.name = name;
        this.description = description;
        this.isProject = isProject;
        this.studentenAnzahl = studentenAnzahl;
        this.students = students;
    }


    public ModulDto() {
    }

    public int getModulID() {
        return this.modulID;
    }

    public void setModulID(int modulID) {
        this.modulID = modulID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIsProject() {
        return this.isProject;
    }

    public boolean getIsProject() {
        return this.isProject;
    }

    public void setIsProject(boolean isProject) {
        this.isProject = isProject;
    }

    public Integer getStudentenAnzahl() {
        return this.studentenAnzahl;
    }

    public void setStudentenAnzahl(Integer studentenAnzahl) {
        this.studentenAnzahl = studentenAnzahl;
    }

    public List<StudentDTO> getStudents() {
        return this.students;
    }

    public void setStudents(List<StudentDTO> students) {
        this.students = students;
    }

    public static class Converter {

        public static ModulDto SimpleDto(Modul modul){
            ModulDto uDto = new ModulDto();
            uDto.setModulID(modul.getModulID());
            uDto.setName(modul.getName());
            uDto.setIsProject(modul.getIsProject());
            uDto.setStudentenAnzahl(modul.studentenAnzahl());
            return uDto;
        }
        public static ModulDto ModuleToDto(Modul modul){
            ModulDto uDto = new ModulDto();
            uDto.setModulID(modul.getModulID());
            uDto.setName(modul.getName());
            uDto.setDescription(modul.getDescription());
            uDto.setIsProject(modul.getIsProject());
            uDto.setStudents(modul.getStudenten().stream().map(Converter::StudentToDto).collect(Collectors.toList()));
            return uDto;
        }

        public static StudentDTO StudentToDto(Student modul){
            StudentDTO uDto = new StudentDTO();
            uDto.setMatNr(modul.getMatNr());
            return uDto;
        }
    }
    
}
