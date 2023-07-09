package com.self.codemaker.excption;

import lombok.Getter;

/**
 * 自定义base错误类
 *
 * @author huangchangjun
 * @date 2023/7/9
 */
@Getter
public class BaseApiException extends RuntimeException {
    /**
     * 异常代码
     */
    protected String code;
    /**
     * 异常信息
     */
    protected String msg;
    /**
     * 异常原因
     */
    protected Throwable cause;

    public BaseApiException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BaseApiException(String msg, Throwable cause) {
        super(msg, cause);
        this.msg = msg;
        this.cause = cause;
    }
}
