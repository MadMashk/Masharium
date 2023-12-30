package mash.masharium.api.auth.request;


import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record SignInRequest(
        @NotNull
        Credentials credentials,
        @NotNull
        String deviceInfo
) {
}
