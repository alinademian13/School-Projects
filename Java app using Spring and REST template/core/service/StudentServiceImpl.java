package lab5.catalog.core.service;

import lab5.catalog.core.model.Student;
import lab5.catalog.core.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger log =
            LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentRepository studentRepository;


    @Override
    public List<Student> getAllStudents() {
        log.trace("getAllStudents --- method entered");

        List<Student> result = studentRepository.findAll();

        log.trace("getAllStudents: result={}", result);

        return result;
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    @Transactional
    public Student updateStudent(Long id, Student student) {
        log.trace("updateStudent: id={}, student={}", id, student);

        Optional<Student> optional = studentRepository.findById(id);

        Student result = optional.orElse(student);
        result.setName(student.getName());
        result.setGrupa(student.getGrupa());

        log.trace("updateStudent: result={}", result);

        return result;
    }

    @Override
    public void deleteById(Long studentId) {
        log.trace("deleteById: studentId={}", studentId);

        studentRepository.deleteById(studentId);

        log.trace("deleteById --- method finished");
    }
}
