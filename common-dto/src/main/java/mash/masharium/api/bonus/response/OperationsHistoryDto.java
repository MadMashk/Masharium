package mash.masharium.api.bonus.response;

import lombok.Data;

import java.util.List;

@Data
public class OperationsHistoryDto {

    List<OperationResponseDto> operations;

}
