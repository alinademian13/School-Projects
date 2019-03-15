package lab5.catalog.web.converter;

import lab5.catalog.core.model.Student;
import lab5.catalog.web.dto.StudentDto;
import org.springframework.stereotype.Component;


@Component
public class StudentConverter extends BaseConverter<Student, StudentDto> {
    @Override
    public Student convertDtoToModel(StudentDto dto) {
        Student student=new Student(dto.getName(),dto.getGrupa());
        student.setId(dto.getId());
        return student;
    }

    @Override
    public StudentDto convertModelToDto(Student student) {
        StudentDto dto = new StudentDto(student.getName(), student.getGrupa());
        dto.setId(student.getId());
        return dto;
    }
}
