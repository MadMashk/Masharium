package mash.masharium.api.auth.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfoResponse {
    UUID userId;

    String firstName;

    String lastName;

    Instant dateOfBirth;

    String email;

    Long phoneNumber;

    List<UserDeviceInfo> userDeviceInfos;

}
