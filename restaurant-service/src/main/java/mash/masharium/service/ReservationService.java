package mash.masharium.service;

import mash.masharium.dto.ReservationRequestDto;
import mash.masharium.dto.ReservationResponseDto;
import mash.masharium.dto.ReservationTableDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ReservationService {
    ReservationResponseDto reserve(ReservationRequestDto requestDto);

    ReservationResponseDto close(UUID id);

    List<ReservationTableDto> getFreeTables(ReservationRequestDto requestDto);

    ReservationResponseDto addClient(ReservationRequestDto requestDto);
}
