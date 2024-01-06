package mash.masharium.api.restaurant.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import mash.masharium.api.restaurant.constant.DishStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DishAvailability {
    DishDto dishDto;
    DishStatus dishStatus;
}