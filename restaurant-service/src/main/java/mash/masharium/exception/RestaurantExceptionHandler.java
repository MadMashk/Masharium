package mash.masharium.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestaurantExceptionHandler {

    @ExceptionHandler(value = {RestaurantServiceException.class})
    public ResponseEntity<String> handleAppException(RestaurantServiceException e) {
        log.info("Error ", e);
        return new ResponseEntity<>(e.getMessage(), e.httpStatus);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<String> handleAssertTrueException(MethodArgumentNotValidException e) {
        log.info("Error ", e);
        return new ResponseEntity<>("Ошибка валидации данных, отправленных в запросе", HttpStatus.BAD_REQUEST);
        }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<String> handleRuntimeException(RuntimeException runtimeException) {
        log.info("Error ", runtimeException);
        return new ResponseEntity<>("Во время обработки запроса возникла ошибка", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
