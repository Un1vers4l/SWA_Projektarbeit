package de.hsos.swa.studiom.StudentsManagement.control;

import java.util.Optional;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;

/**
 * @author Joana Wegener
 */

public interface StudentService {
    public Optional<Student> createStudent(String name);

    public Optional<Student> changeStudent(Student newStudent);

    public boolean deleteStudent(int matNr);

    public Optional<Student> getStudent(int matNr);
}
