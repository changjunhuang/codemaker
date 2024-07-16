package com.self.codemaker.data;

import com.self.codemaker.enums.ApiResponseCode;
import com.self.codemaker.excption.ApiServiceException;
import lombok.Data;

/**
 * @author huangchangjun
 * @date 2023/07/09
 */
@Data
public class ApiResponse<T> {

    /**
     * 响应码
     */
    protected String code;

    /**
     * 响应信息
     */
    protected String message;

    /**
     * 数据
     */
    protected T data;

    private String traceId;

    public ApiResponse() {
    }

    public static <T> ApiResponse<T> buildSuccess() {
        ApiResponse<T> response = new ApiResponse<>();
        response.code = ApiResponseCode.SUCCESS.getCode();
        response.message = ApiResponseCode.SUCCESS.getMessage();
        return response;
    }

    public static <T> ApiResponse<T> buildSuccess(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.data = data;
        response.code = ApiResponseCode.SUCCESS.getCode();
        response.message = ApiResponseCode.SUCCESS.getMessage();
        return response;
    }

    /**
     * 带异常判断的返回,配合校验
     *
     * @param apiResponseCode
     * @param e
     * @param <T>
     * @return
     */
    public static <T> ApiResponse<T> buildFailure(ApiResponseCode apiResponseCode, Throwable e) {
        if (e instanceof ApiServiceException) {
            return ((ApiServiceException) e).getResponse();
        } else if (e instanceof IllegalArgumentException) {
            //  处理Assert出的异常
            return ApiResponse.buildFailure(ApiResponseCode.PARAM_ERROR.setMessage(e.getMessage()));
        } else {
            ApiResponse<T> response = new ApiResponse<>();
            response.code = apiResponseCode.getCode();
            response.message = apiResponseCode.getMessage();
            return response;
        }
    }

    public static <T> ApiResponse<T> buildFailure(ApiResponseCode apiResponseCode) {
        ApiResponse<T> response = new ApiResponse<>();
        response.code = apiResponseCode.getCode();
        response.message = apiResponseCode.getMessage();
        return response;
    }

    public static <T> ApiResponse<T> buildFailure(String errorCode, String errorMsg) {
        ApiResponse<T> response = new ApiResponse<>();
        response.code = errorCode;
        response.message = errorMsg;
        return response;
    }

    @Override
    public String toString() {
        return "ApiResponse{" + "traceId='" + traceId + '\'' + ", code='" + code + '\'' + ", message='" + message + '\'' + ", data=" + data + '}';
    }
}
