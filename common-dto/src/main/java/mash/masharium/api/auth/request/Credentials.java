package mash.masharium.api.auth.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

@Validated
public record Credentials(
        @NotNull
        String login,
        @NotNull
        @Size(min = 9, max = 30)
        String password
) {
}
