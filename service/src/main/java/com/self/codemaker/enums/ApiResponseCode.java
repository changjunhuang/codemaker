package com.self.codemaker.enums;

import lombok.Getter;

/**
 * 统一返回码基类
 *
 * @author huangchangjun
 * @date 2023/7/9
 */
@Getter
public enum ApiResponseCode {
    SUCCESS("0", "成功"),
    FAILURE("9000", "处理失败"),
    DATA_EMPTY("9995", "数据为空"),
    DB_ERROR("9996", "数据库错误"),
    PARAM_NULL("9997", "参数为空"),
    PARAM_ERROR("9998", "参数错误"),
    SYSTEM_ERROR("9999", "系统异常");

    /**
     * 通用错误码值
     */
    private String code;

    /**
     * 错误描述
     */
    private String message;

    ApiResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResponseCode setMessage(String message) {
        this.message = message;
        return this;
    }
}
