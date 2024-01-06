package mash.masharium.mapper;

import mash.masharium.entity.Component;
import mash.masharium.entity.Dish;
import mash.masharium.entity.DishComponent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface DishComponentMapper {

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "component", source = "component")
    @Mapping(target = "quantity", source = "quantity")
    DishComponent mapComponentAndDishAndQuantityToDishComponent(Component component, Dish dish, BigDecimal quantity);
}
