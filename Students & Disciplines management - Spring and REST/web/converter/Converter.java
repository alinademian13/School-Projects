package lab5.catalog.web.converter;


import lab5.catalog.core.model.BaseEntity;
import lab5.catalog.web.dto.BaseDto;

public interface Converter<Model extends BaseEntity<Long>, Dto extends BaseDto> {

    Model convertDtoToModel(Dto dto);

    Dto convertModelToDto(Model model);

}

