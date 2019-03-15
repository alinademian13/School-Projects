package lab5.catalog.web.dto;

import lombok.*;

import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StudentsDto {
    private Set<StudentDto> students;
}
