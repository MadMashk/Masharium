package mash.masharium.api.restaurant.common;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import mash.masharium.api.restaurant.constant.MeasureType;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComponentDto {

    UUID id;

    String name;

    BigDecimal quantity;

    MeasureType measureType;

    BigDecimal price;
}
