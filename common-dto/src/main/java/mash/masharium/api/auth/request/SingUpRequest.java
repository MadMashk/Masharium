package mash.masharium.api.auth.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import mash.masharium.api.auth.constant.UserRoleType;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.util.Set;


@Validated
public record SingUpRequest(
        @NotNull
        Credentials credentials,
        @NotNull
        @Size(min = 1, max = 25)
        String userName,
        @NotNull
        @Size(min = 1, max = 25)
        String firstName,
        @NotNull
        @Size(min = 1, max = 25)
        String lastName,
        @NotNull
        Instant dateOfBirth,
        @NotNull
        Set<UserRoleType> roles,
        @NotNull
        String deviceInfo,
        @Nullable
        String email,
        @Nullable
        Long phoneNumber
) {
}
