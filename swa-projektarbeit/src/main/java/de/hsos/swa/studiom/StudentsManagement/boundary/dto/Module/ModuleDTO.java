/**
 * @author Joana Wegener (855518)
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-02-05 15:02:55
 * @modify date 2022-02-11 08:57:59
 * @desc [description]
 */
package de.hsos.swa.studiom.StudentsManagement.boundary.dto.Module;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import de.hsos.swa.studiom.ModulManagment.entity.Modul;
import de.hsos.swa.studiom.StudentsManagement.boundary.dto.Student.newStudentDTO;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;

public class ModuleDTO {
    public String name;
    public int id;
    public String description;
    @JsonInclude(Include.NON_DEFAULT)
    public boolean isProject;
    public Set<newStudentDTO> students;

    public ModuleDTO(String name, int id, String description, boolean isProject) {
        students = new HashSet<>();
        this.name = name;
        this.id = id;
        this.description = description;
        this.isProject = isProject;
    }
    

    public ModuleDTO() {
    }

    public static class Converter {
        public static ModuleDTO toHTTPDTO(Modul module) {
            ModuleDTO mDTO = new ModuleDTO(module.getName(), module.getModulID(), module.getDescription(),
                    module.getIsProject());
            for (Student stud : module.getStudenten()) {
                mDTO.students.add(newStudentDTO.Converter.toDTO(stud));
            }
            return mDTO;
        }
    }
}
