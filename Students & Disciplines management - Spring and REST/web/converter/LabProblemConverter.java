package lab5.catalog.web.converter;

import lab5.catalog.core.model.LabProblem;
import lab5.catalog.web.dto.LabProblemDto;
import org.springframework.stereotype.Component;

@Component
public class LabProblemConverter extends BaseConverter<LabProblem, LabProblemDto> {

    @Override
    public LabProblem convertDtoToModel(LabProblemDto dto) {
        LabProblem labProblem = new LabProblem(dto.getDiscipline(), dto.getLabNumber());
        labProblem.setId(dto.getId());
        return labProblem;
    }

    @Override
    public LabProblemDto convertModelToDto(LabProblem labProblem) {
        LabProblemDto dto = new LabProblemDto(labProblem.getDiscipline(), labProblem.getLabNumber());
        dto.setId(labProblem.getId());
        return dto;
    }
}
