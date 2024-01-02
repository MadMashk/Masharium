package mash.masharium.mapper;

import mash.masharium.dto.response.ParameterResponseDto;
import mash.masharium.entity.Parameter;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParameterMapper {

    ParameterResponseDto toDto(Parameter parameter);

    List<ParameterResponseDto> toListDto(List<Parameter> parameters);

}
