package mash.masharium.exception.model;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import mash.masharium.exception.OrderServiceException;
import org.springframework.http.HttpStatus;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ForbiddenException extends OrderServiceException {
    public ForbiddenException(String reasonText) {
        super(HttpStatus.FORBIDDEN, reasonText);
    }
}
