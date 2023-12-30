package mash.masharium.exception;

import org.springframework.http.HttpStatus;

public class NotFountException extends AuthServiceException{
    public NotFountException(String reasonText) {
        super(
                HttpStatus.NOT_FOUND,
                reasonText
        );
    }
}
