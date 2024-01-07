package mash.masharium.exception;

import lombok.extern.slf4j.Slf4j;
import mash.masharium.dto.response.BaseResponse;
import mash.masharium.dto.response.BaseResponseUtils;
import mash.masharium.dto.response.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class OrderExceptionHandler {

    @ExceptionHandler(value = {OrderServiceException.class})
    public ResponseEntity<BaseResponse<Void>> handleAppException(OrderServiceException e) {
        return new ResponseEntity<>(BaseResponseUtils.getUnsuccessfulBaseResponse(new Error(e.getMessage())), e.httpStatus);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<BaseResponse<Void>> handleRuntimeException(RuntimeException runtimeException) {
        log.info("Error ", runtimeException);
        return new ResponseEntity<>(BaseResponseUtils.getUnsuccessfulBaseResponse(new Error("Во время обработки запроса возникла ошибка")), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}