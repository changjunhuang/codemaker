package com.self.codemaker.enums;

import com.self.codemaker.data.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author huangchangjun
 * @date 2024/7/15
 */
@Getter
@AllArgsConstructor
public enum ResponseCode {

    //默认
    SUCCESS("0", "成功"),
    FAILURE("49000", "处理失败"),
    SYSTEM_ERROR("49999", "系统异常"),
    SYSTEM_BUSY("49998", "请勿频繁操作"),
    PROCESSING("49001", "请求处理中，请勿重复提交"),

    //41开头参数错误
    PARAM_NULL("41001", "参数为空"),
    PARAM_ERROR("41002", "参数错误"),

    //42开头是数据库异常
    DATA_EMPTY("42001", "数据为空"),
    USER_NOT_FOUND("42000", "没有此用户"),
    DATA_UPDATE_ERROR("42002", "数据库操作异常"),

    //51开头参数错误
    NOT_EXIST("51001", "该记录不存在"),
    IS_EXIST("51002", "该记录已存在"),
    ;

    private String code;
    private String message;

    public static <T> ApiResponse<T> buildApiResponseFailure(ResponseCode responseCode) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(responseCode.getCode());
        response.setMessage(responseCode.getMessage());
        return response;
    }

    public static <T> ApiResponse<T> buildApiResponseFailure(String code, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

    public static <T> ApiResponse<T> buildApiResponseSuccess() {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(ApiResponseCode.SUCCESS.getCode());
        response.setMessage(ApiResponseCode.SUCCESS.getMessage());
        return response;
    }

    public static <T> ApiResponse<T> buildApiResponseSuccess(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setData(data);
        response.setCode(ApiResponseCode.SUCCESS.getCode());
        response.setMessage(ApiResponseCode.SUCCESS.getMessage());
        return response;
    }

    public static ResponseCode getResponseCode(String code) {
        for (ResponseCode responseCode : values()) {
            if (responseCode.getCode().equals(code)) {
                return responseCode;
            }
        }
        return null;
    }

}
