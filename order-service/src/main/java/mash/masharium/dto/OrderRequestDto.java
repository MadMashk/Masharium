package mash.masharium.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderRequestDto {

    private UUID clientId;

    private String address;

    private List<PositionDto> positions;

}
