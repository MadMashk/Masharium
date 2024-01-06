package mash.masharium.mapper;

import mash.masharium.api.restaurant.common.DishAvailability;
import mash.masharium.api.restaurant.common.DishDto;
import mash.masharium.api.restaurant.constant.DishStatus;
import mash.masharium.api.restaurant.request.DishCreationRequest;
import mash.masharium.entity.Dish;
import mash.masharium.entity.DishComponent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = ComponentMapper.class)
public abstract class DishMapper {

    @Autowired
    private ComponentMapper componentMapper;

    public abstract List<Dish> mapDishCreationRequestToDish(List<DishCreationRequest> dishCreationRequest);

    @Mapping(target = "dishDto", source = "dish", qualifiedByName = "dishToDishDto")
    public abstract DishAvailability mapToDishAvailability(Dish dish, DishStatus dishStatus);

    public DishDto mapDishComponentsOfOneDishToDishDto(List<DishComponent> dishComponents) {
        DishDto dishDto = dishToDishDto(dishComponents.stream().findAny().orElseThrow().getDish());
        dishDto.setComponentDtoList(dishComponents.stream()
                .map(dishComponent -> componentMapper.dishComponentToComponentDto(dishComponent)).toList());
        return dishDto;
    }


    @Named("dishToDishDto")
    @Mapping(target = "componentDtoList", source = "dishComponents", qualifiedByName = "dishComponentsToComponentDtos")
    public abstract DishDto dishToDishDto(Dish dish);


    public List<DishDto> dishesToDishDtos(List<Dish> dishes) {
        return dishes.stream().map(this::dishToDishDto).toList();
    }


}
