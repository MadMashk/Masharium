package mash.masharium.exception.model;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import mash.masharium.exception.KitchenServiceException;
import org.springframework.http.HttpStatus;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorizeException extends KitchenServiceException {
    public AuthorizeException(String reasonText) {
        super(HttpStatus.UNAUTHORIZED, reasonText);
    }
}
