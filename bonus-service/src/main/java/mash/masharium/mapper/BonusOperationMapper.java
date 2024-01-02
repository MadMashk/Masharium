package mash.masharium.mapper;

import mash.masharium.api.bonus.response.OperationResponseDto;
import mash.masharium.entity.BonusOperation;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BonusOperationMapper {

    OperationResponseDto toDto(BonusOperation operation);

    List<OperationResponseDto> toListDto(List<BonusOperation> operations);

}
