package mash.masharium.api.restaurant.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import mash.masharium.api.restaurant.constant.MeasureType;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComponentCreationRequest {
    @NotNull
    String name;

    @NotNull
    BigDecimal quantity;

    @NotNull
    MeasureType measureType;

    @NotNull
    BigDecimal price;
}
