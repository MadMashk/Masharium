package mash.masharium.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mash.masharium.dto.response.ParameterRequestDto;
import mash.masharium.dto.response.ParameterResponseDto;
import mash.masharium.service.ParameterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Параметры настроек")
@RestController
@RequestMapping("/parameters")
@RequiredArgsConstructor
public class ParameterController {

    private final ParameterService parameterService;

    @Operation(description = "Получение параметра из таблиц с настройками")
    @GetMapping("/name")
    public String getParameter(@RequestParam String parameterName) {
        return parameterService.getValue(parameterName);
    }

    @Operation(description = "Получение списка параметров")
    @GetMapping("/all")
    public List<ParameterResponseDto> getAll() {
        return parameterService.getAll();
    }

    @Operation(description = "Создание параметра")
    @PostMapping("/creation")
    public ParameterResponseDto create(@RequestBody ParameterRequestDto requestDto) {
        return parameterService.createOrUpdate(requestDto);
    }
}
