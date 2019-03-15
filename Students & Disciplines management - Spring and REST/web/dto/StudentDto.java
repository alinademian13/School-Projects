package lab5.catalog.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class StudentDto extends BaseDto {
    private String name;
    private int grupa;
}
