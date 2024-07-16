package com.self.codemaker.excption;

import com.self.codemaker.enums.ResponseCode;
import lombok.Getter;

/**
 * 业务层使用
 *
 * @author huangchangjun
 * @date 2024/7/15
 */
@Getter
public class BizServiceException extends BaseApiException {

    public BizServiceException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public BizServiceException(String code, String msg, Exception cause) {
        super(msg, cause);
        this.code = code;
    }

    public static void throwBizException(String code, String msg) {
        throw new BizServiceException(code, msg);
    }

    public static void throwBizException(ResponseCode responseCode) {
        throw new BizServiceException(responseCode.getCode(), responseCode.getMessage());
    }

    public static void throwBizException(ResponseCode responseCode, Exception cause) {
        throw new BizServiceException(responseCode.getCode(), responseCode.getMessage(), cause);
    }
}
