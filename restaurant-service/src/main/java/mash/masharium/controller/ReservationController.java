package mash.masharium.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mash.masharium.dto.ReservationRequestDto;
import mash.masharium.dto.ReservationResponseDto;
import mash.masharium.dto.ReservationTableDto;
import mash.masharium.service.ReservationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "Резервирование")
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;


    @Operation(summary = "зарезервировать стол")
    @PostMapping
    public ReservationResponseDto reserve(@Valid @RequestBody ReservationRequestDto requestDto) {
        return reservationService.reserve(requestDto);
    }

    @Operation(summary = "добавляем клиента к резервации")
    @PatchMapping("/{id}")
    public ReservationResponseDto addClint(@RequestBody ReservationRequestDto requestDto) {
        return reservationService.addClient(requestDto);
    }

    @Operation(summary = "закрыть резервацию стола")
    @PatchMapping("/{id}/close")
    public ReservationResponseDto closeReservation(@PathVariable UUID id) {
        return reservationService.close(id);
    }

    @Operation(summary = "проверить есть ли свободный стол в определенное время")
    @PostMapping("/free-tables")
    public List<ReservationTableDto> getFreeTables(@Valid @RequestBody ReservationRequestDto requestDto) {
        return reservationService.getFreeTables(requestDto);
    }
}
