package mash.masharium.service;

import mash.masharium.dto.response.ParameterRequestDto;
import mash.masharium.dto.response.ParameterResponseDto;
import mash.masharium.entity.Parameter;

import java.util.List;

public interface ParameterService {

    List<ParameterResponseDto> getAll();

    ParameterResponseDto createOrUpdate(ParameterRequestDto requestDto);

    String getValue(String name);
}
