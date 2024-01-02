package mash.masharium.exception.model;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import mash.masharium.exception.KitchenServiceException;
import org.springframework.http.HttpStatus;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ValidationException extends KitchenServiceException {
    public ValidationException(String reasonText) {
        super(HttpStatus.BAD_REQUEST, reasonText);
    }
}
