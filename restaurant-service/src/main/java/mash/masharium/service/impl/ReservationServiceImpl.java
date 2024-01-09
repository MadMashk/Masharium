package mash.masharium.service.impl;

import lombok.RequiredArgsConstructor;
import mash.masharium.dto.ReservationRequestDto;
import mash.masharium.dto.ReservationResponseDto;
import mash.masharium.dto.ReservationTableDto;
import mash.masharium.entity.RestaurantTable;
import mash.masharium.entity.TableClient;
import mash.masharium.entity.TableReservation;
import mash.masharium.exception.NotFountException;
import mash.masharium.mapper.ReservationMapper;
import mash.masharium.mapper.ReservationTableMapper;
import mash.masharium.repository.ReservationClientRepository;
import mash.masharium.repository.ReservationRepository;
import mash.masharium.repository.RestaurantTableRepository;
import mash.masharium.service.ReservationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final ReservationClientRepository reservationClientRepository;

    private final RestaurantTableRepository restaurantTableRepository;

    private final ReservationMapper reservationMapper;

    private final ReservationTableMapper reservationTableMapper;

    @Override
    @Transactional
    public ReservationResponseDto reserve(ReservationRequestDto requestDto) {
        RestaurantTable table = restaurantTableRepository.findById(requestDto.getTableId()).orElseThrow(
                () -> new NotFountException(restaurantTableRepository.getErrorMassage())
        );
        TableReservation tableReservation = new TableReservation();
        tableReservation.setReservationDate(requestDto.getTime());
        tableReservation.setReservationEnd(requestDto.getEndTime());
        tableReservation.setRestaurantTable(table);
        tableReservation.setName(requestDto.getName());
        return reservationMapper.toDto(reservationRepository.save(tableReservation));
    }

    @Override
    @Transactional
    public ReservationResponseDto close(UUID id) {
        TableReservation tableReservation = reservationRepository.findById(id).orElseThrow(
                () -> new NotFountException(reservationRepository.getErrorMassage())
        );
        tableReservation.setReservationEnd(LocalDateTime.now());
        return reservationMapper.toDto(tableReservation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationTableDto> getFreeTables(ReservationRequestDto requestDto) {
        if (Objects.isNull(requestDto.getEndTime())) {
            requestDto.setEndTime(requestDto.getTime().plusHours(2));
        }
        List<RestaurantTable> restaurantTables = restaurantTableRepository.findFreeInTime(requestDto.getTime(), requestDto.getEndTime());
        return reservationTableMapper.toListDto(restaurantTables);
    }

    @Override
    @Transactional
    public ReservationResponseDto addClient(ReservationRequestDto requestDto) {
        TableReservation tableReservation = reservationRepository.findById(requestDto.getId()).orElseThrow(
                () -> new NotFountException(reservationRepository.getErrorMassage())
        );
        TableClient tableClient = new TableClient();
        tableClient.setUserId(requestDto.getClientId());
        tableClient.setTableReservation(tableReservation);
        tableReservation.getTableClients().add(reservationClientRepository.save(tableClient));
        reservationRepository.save(tableReservation);
        return reservationMapper.toDto(tableReservation);
    }
}
