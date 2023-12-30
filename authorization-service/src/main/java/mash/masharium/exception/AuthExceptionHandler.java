package mash.masharium.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(value = {AuthServiceException.class})
    public ResponseEntity<String> handleAppException(AuthServiceException e) {
        log.info("Error ", e);
        return new ResponseEntity<>(e.getMessage(), e.httpStatus);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<String> handleRuntimeException(RuntimeException runtimeException) {
        log.info("Error ", runtimeException);
        return new ResponseEntity<>("Во время обработки запроса возникла ошибка", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
