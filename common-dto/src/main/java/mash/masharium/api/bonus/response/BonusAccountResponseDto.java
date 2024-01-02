package mash.masharium.api.bonus.response;

import lombok.Data;

import java.util.UUID;

@Data
public class BonusAccountResponseDto {
    private UUID userId;
    private UUID id;
    private Integer balance;
}
