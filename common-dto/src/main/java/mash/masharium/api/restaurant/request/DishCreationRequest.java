package mash.masharium.api.restaurant.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import mash.masharium.api.restaurant.constant.DishType;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DishCreationRequest {
    @NotNull
    String name;
    @NotNull
    String description;
    @NotNull
    DishType dishType;
    @NotNull
    BigDecimal price;
}
