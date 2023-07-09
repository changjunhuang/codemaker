package com.self.codemaker.handler;

import com.self.codemaker.data.ApiResponse;
import com.self.codemaker.enums.ApiResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 *
 * @author huangchangjun
 * @date 2023/7/9
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandle {

    /**
     * 全局捕获空指针异常
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<String> nullPointException(HttpServletRequest request, Throwable e) {
        log.info("捕获了一个空指针异常!", e);
        return ApiResponse.buildFailure(ApiResponseCode.FAILURE);
    }

    /**
     * http接口请求 获取对应状态码
     *
     * @param request
     * @return
     */
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
