package mash.masharium.api.restaurant.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import mash.masharium.api.restaurant.constant.DishType;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DishDto {

    UUID id;

    String name;

    String description;

    DishType dishType;

    BigDecimal price;

    List<ComponentDto> componentDtoList;

}
