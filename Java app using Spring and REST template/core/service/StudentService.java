package lab5.catalog.core.service;

import lab5.catalog.core.model.Student;

import java.util.List;


public interface StudentService {
    List<Student> getAllStudents();

    Student saveStudent(Student student);

    Student updateStudent(Long id, Student student);

    void deleteById(Long studentId);
}
