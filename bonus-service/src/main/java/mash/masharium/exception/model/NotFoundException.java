package mash.masharium.exception.model;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import mash.masharium.exception.BonusServiceException;
import org.springframework.http.HttpStatus;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotFoundException extends BonusServiceException {
    public NotFoundException(String reasonText) {
        super(HttpStatus.NOT_FOUND, reasonText);
    }
}