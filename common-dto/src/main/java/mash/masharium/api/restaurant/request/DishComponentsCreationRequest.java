package mash.masharium.api.restaurant.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DishComponentsCreationRequest {
    UUID componentId;
    BigDecimal quantity;
}
