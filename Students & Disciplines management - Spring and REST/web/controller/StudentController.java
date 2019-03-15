package lab5.catalog.web.controller;

import lab5.catalog.core.model.Student;
import lab5.catalog.core.service.StudentService;
import lab5.catalog.web.converter.StudentConverter;
import lab5.catalog.web.dto.StudentDto;
import lab5.catalog.web.dto.StudentsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
public class StudentController {
    private static final Logger log =
            LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentConverter studentConverter;


    @RequestMapping(value = "/students", method = RequestMethod.GET)
    StudentsDto getStudents() {
        log.trace("getStudents --- method entered");

        List<Student> students = studentService.getAllStudents();
        Set<StudentDto> studentDtos = studentConverter.convertModelsToDtos(
                students);
        StudentsDto result = new StudentsDto(studentDtos);

        log.trace("getStudents: result={}", result);

        return result;
    }

    @RequestMapping(value = "/students", method = RequestMethod.POST)
    StudentDto saveStudent(@RequestBody StudentDto dto) {
        log.trace("saveStudent: dto={}", dto);

        Student student =
                studentService.saveStudent(
                        studentConverter.convertDtoToModel(dto));
        StudentDto result = studentConverter.convertModelToDto(student);

        log.trace("saveStudent: result={}", result);

        return result;
    }

    @RequestMapping(value = "/students/{studentId}", method = RequestMethod.PUT)
    StudentDto updateStudent(@PathVariable Long studentId,
            @RequestBody StudentDto dto) {
        log.trace("updateStudent: studentId={}, dto={}", studentId, dto);

        Student st = studentService.updateStudent(
                studentId,
                studentConverter.convertDtoToModel(dto));
        StudentDto result = studentConverter.convertModelToDto(st);

        log.trace("updateStudent: result={}", result);

        return result;
    }

    @RequestMapping(value = "/students/{studentId}", method =
            RequestMethod.DELETE)
    ResponseEntity<?> deleteStudent(@PathVariable Long studentId) {
        log.trace("deleteStudent: studentId={}", studentId);

        studentService.deleteById(studentId);

        log.trace("deleteStudent --- method finished");

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
