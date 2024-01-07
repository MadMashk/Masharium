package mash.masharium.exception;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenValidationException extends AuthServiceException {

    public TokenValidationException(String reasonText) {
        super(
                HttpStatus.FORBIDDEN,
                reasonText
        );
    }
}
