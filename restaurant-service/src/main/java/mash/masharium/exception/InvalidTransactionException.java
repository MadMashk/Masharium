package mash.masharium.exception;

import org.springframework.http.HttpStatus;


public class InvalidTransactionException extends RestaurantServiceException {

    public InvalidTransactionException(String reasonText) {
        super(
                HttpStatus.NOT_ACCEPTABLE,
                reasonText
        );
    }
}
