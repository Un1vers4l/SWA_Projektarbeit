/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 14:13:36
 * @modify date 2022-01-22 14:13:36
 * @desc [description]
 */
package de.hsos.swa.studiom.StudentsManagement.control;

import java.util.Optional;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;

public interface StudentService {
    public Optional<Student> createStudent(String name);

    public Optional<Student> changeStudent(int matNr, Student newStudent);

    public boolean deleteStudent(int matNr);

    public Optional<Student> getStudent(int matNr);
}
