package lab5.catalog.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class LabProblemDto extends BaseDto {

    private String discipline;
    private int labNumber;
}
