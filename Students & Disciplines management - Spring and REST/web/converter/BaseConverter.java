package lab5.catalog.web.converter;


import lab5.catalog.core.model.BaseEntity;
import lab5.catalog.web.dto.BaseDto;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


public abstract class BaseConverter<Model extends BaseEntity<Long>, Dto extends BaseDto>
        implements Converter<Model, Dto> {


    public Set<Long> convertModelsToIDs(Set<Model> models) {
        return models.stream()
                .map(model -> model.getId())
                .collect(Collectors.toSet());
    }

    public Set<Long> convertDTOsToIDs(Set<Dto> dtos) {
        return dtos.stream()
                .map(dto -> dto.getId())
                .collect(Collectors.toSet());
    }

    public Set<Dto> convertModelsToDtos(Collection<Model> models) {
        return models.stream()
                .map(model -> convertModelToDto(model))
                .collect(Collectors.toSet());
    }

    public Set<Model> convertDtosToModels(Set<Dto> dtos) {
        return dtos.stream()
                .map(dto -> convertDtoToModel(dto))
                .collect(Collectors.toSet());
    }
}
