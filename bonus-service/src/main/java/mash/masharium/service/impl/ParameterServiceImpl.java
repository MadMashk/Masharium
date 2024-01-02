package mash.masharium.service.impl;

import lombok.RequiredArgsConstructor;
import mash.masharium.dto.response.ParameterRequestDto;
import mash.masharium.dto.response.ParameterResponseDto;
import mash.masharium.entity.Parameter;
import mash.masharium.exception.model.NotFoundException;
import mash.masharium.mapper.ParameterMapper;
import mash.masharium.repository.ParameterRepository;
import mash.masharium.service.ParameterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParameterServiceImpl implements ParameterService {

    private final ParameterRepository parameterRepository;

    private final ParameterMapper parameterMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ParameterResponseDto> getAll() {
        return parameterMapper.toListDto(parameterRepository.findAll());
    }

    @Override
    @Transactional
    public ParameterResponseDto createOrUpdate(ParameterRequestDto requestDto) {
        Optional<Parameter> optional = parameterRepository.findByName(requestDto.getName());
        Parameter parameter;
        if (optional.isPresent()) {
            parameter = optional.get();
        } else {
            parameter = new Parameter();
            parameter.setName(parameter.getName());
        }
        parameter.setDescription(requestDto.getDescription());
        parameter.setValue(requestDto.getValue());
        parameter.setName(requestDto.getName());
        return parameterMapper.toDto(parameterRepository.save(parameter));
    }

    @Override
    @Transactional(readOnly = true)
    public String getValue(String name) {
        return parameterRepository.findByName(name).orElseThrow(
                () -> new NotFoundException("Не найден параметр с наименованием " + name)
        ).getValue();
    }
}
