package mash.masharium.exception;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;


@FieldDefaults(level = AccessLevel.PROTECTED)
public class AuthServiceException extends RuntimeException {
    final HttpStatus httpStatus;

    protected AuthServiceException(HttpStatus httpStatus, String reasonText){
        super(reasonText);
        this.httpStatus = httpStatus;
    }
}
