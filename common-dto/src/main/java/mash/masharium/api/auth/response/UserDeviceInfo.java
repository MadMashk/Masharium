package mash.masharium.api.auth.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDeviceInfo {

    UUID id;

    Instant enterDate;

    String deviceInfo;

    Boolean isConnected;
}
