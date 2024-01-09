package mash.masharium.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mash.masharium.api.kitchen.TicketRequestDto;
import mash.masharium.dto.TicketResponseDto;
import mash.masharium.service.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Тикеты")
@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Operation(description = "Создание тикета", tags = "Тикеты", hidden = true)
    @PostMapping
    public void create(@RequestBody TicketRequestDto dto) {
        ticketService.create(dto);
    }

    @Operation(description = "Получение списка тикетов", tags = "Тикеты")
    @GetMapping
    public List<TicketResponseDto> getAll() {
        return ticketService.getAll();
    }

    @Operation(description = "Получение списка активных тикетов", tags = "Тикеты")
    @GetMapping("/active")
    public List<TicketResponseDto> getAllActive() {
        return ticketService.getAllActive();
    }

    @Operation(description = "Взять тикет в работу", tags = "Тикеты")
    @PatchMapping
    public void take(UUID ticketId, String mockToken) {
        ticketService.take(ticketId, mockToken);
    }

    @Operation(description = "Завершить тикет", tags = "Тикеты")
    @PatchMapping("/completion")
    public void complete(UUID ticketId) {
        ticketService.complete(ticketId);
    }

}
