package com.self.codemaker.excption;

import com.self.codemaker.data.ApiResponse;
import com.self.codemaker.enums.ApiResponseCode;
import lombok.Getter;

/**
 * @author huangchangjun
 * @date 2023/7/9
 */
@Getter
public class ApiServiceException extends BaseApiException {
    ApiResponse response;

    public <T> ApiServiceException(ApiResponse<T> response) {
        super(response.getMessage());
        this.msg = response.getMessage();
        this.code = response.getCode();
        this.response = response;
    }

    public ApiServiceException(ApiResponseCode apiResponseCode, String msg) {
        super(msg);
        this.code = apiResponseCode.getCode();
        this.msg = msg;
    }
}
