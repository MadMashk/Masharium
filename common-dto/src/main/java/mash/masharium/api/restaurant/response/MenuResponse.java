package mash.masharium.api.restaurant.response;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import mash.masharium.api.restaurant.common.DishAvailability;
import mash.masharium.api.restaurant.common.DishDto;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Data
@Validated
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MenuResponse {

     @Nullable
     UUID id;

     @NotNull
     String description;

     @Nullable
     List<DishAvailability> dishDtoList;

}
