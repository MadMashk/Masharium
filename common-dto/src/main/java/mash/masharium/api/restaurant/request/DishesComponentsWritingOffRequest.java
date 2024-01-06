package mash.masharium.api.restaurant.request;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

import java.util.Map;
import java.util.UUID;

@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DishesComponentsWritingOffRequest {
    UUID orderId;
    Map<UUID, Integer> dishesQuantityMap;
}
