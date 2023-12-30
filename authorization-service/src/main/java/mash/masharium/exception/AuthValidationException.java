package mash.masharium.exception;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthValidationException extends AuthServiceException {

    public AuthValidationException(String reasonText) {
        super(
                HttpStatus.FORBIDDEN,
                reasonText
        );
    }
}
