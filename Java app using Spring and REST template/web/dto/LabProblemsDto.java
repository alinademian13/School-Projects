package lab5.catalog.web.dto;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LabProblemsDto {

    private Set<LabProblemDto> labProblems;
}
