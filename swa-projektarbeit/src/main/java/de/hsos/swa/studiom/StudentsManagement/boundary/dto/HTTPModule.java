package de.hsos.swa.studiom.StudentsManagement.boundary.dto;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-02-05 15:02:55
 * @modify date 2022-02-05 15:51:29
 * @desc [description]
 */

import de.hsos.swa.studiom.ModulManagment.entity.Modul;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;

public class HTTPModule {
    public String name;
    public int id;
    public String description;
    public boolean isProject;
    public Set<newStudentDTO> students = new HashSet<>();

    public HTTPModule(String name, int id, String description, boolean isProject) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.isProject = isProject;
    }

    public static class Converter {
        public static HTTPModule toDTO(Modul module) {
            HTTPModule httpModule = new HTTPModule(module.getName(), module.getModulID(), module.getDescription(),
                    module.getIsProject());
            for (Student stud : module.getStudenten()) {
                httpModule.students.add(newStudentDTO.Converter.toDTO(stud));
            }
            return httpModule;

        }
    }
}
