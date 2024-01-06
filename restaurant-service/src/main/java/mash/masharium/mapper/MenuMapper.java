package mash.masharium.mapper;

import mash.masharium.api.restaurant.common.DishAvailability;
import mash.masharium.api.restaurant.request.MenuCreationRequest;
import mash.masharium.api.restaurant.response.MenuResponse;
import mash.masharium.entity.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = DishMapper.class)
public interface MenuMapper {

    Menu mapMenuCreationRequestToMenu(MenuCreationRequest menuCreationRequest);

    @Mapping(target = "dishDtoList", source = "dishAvailabilities")
    MenuResponse mapMenuToMenuResponseDto(Menu menu, List<DishAvailability> dishAvailabilities);


}
