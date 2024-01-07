package mash.masharium.dto.response;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BaseResponseUtils {

    public <T> BaseResponse<T> getSuccessBaseResponse(T body) {
        return BaseResponse.<T>builder().success(true)
                .body(body)
                .error(null)
                .build();
    }

    public BaseResponse<Void> getUnsuccessfulBaseResponse(Error error) {
        return BaseResponse.<Void>builder().success(false)
                .body(null)
                .error(error)
                .build();
    }
}
