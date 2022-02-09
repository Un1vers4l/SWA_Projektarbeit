/**
 * @author Joana Wegener
 * @email joana.wegener@hs-osnabrueck.de
 * @create date 2022-01-22 14:13:36
 * @modify date 2022-01-22 14:13:36
 * @desc [description]
 */
package de.hsos.swa.studiom.StudentsManagement.control;

import java.util.List;
import java.util.Optional;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.shared.exceptions.CanNotGeneratUserExeption;
import de.hsos.swa.studiom.shared.exceptions.EntityNotFoundException;

public interface StudentService {
    public Optional<Student> createStudent(String voranme, String nachname) throws CanNotGeneratUserExeption;

    public Optional<Student> changeStudent(int matNr, Student newStudent) throws EntityNotFoundException;

    public boolean deleteStudent(int matNr) throws EntityNotFoundException;

    public Optional<Student> getStudent(int matNr) throws EntityNotFoundException;

    public Optional<List<Student>> getAllStudent();
}
