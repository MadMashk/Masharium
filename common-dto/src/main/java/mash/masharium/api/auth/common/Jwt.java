package mash.masharium.api.auth.common;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record Jwt(
        @NotNull
        String token
) {
}
