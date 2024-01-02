package mash.masharium.mapper;

import mash.masharium.api.bonus.response.BonusAccountResponseDto;
import mash.masharium.entity.BonusAccount;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = BonusOperationMapper.class)
public interface BonusAccountMapper {

    BonusAccountResponseDto toDto(BonusAccount account);

    List<BonusAccountResponseDto> toListDto(List<BonusAccount> accounts);
}
