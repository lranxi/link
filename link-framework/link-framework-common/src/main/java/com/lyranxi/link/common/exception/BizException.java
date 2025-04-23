package com.lyranxi.link.common.exception;

import com.lyranxi.link.common.enums.BaseResponseCodeEnum;
import lombok.Getter;

import java.util.Map;

/**
 * 业务异常
 *
 * @author ranxi
 * @date 2025-02-28 12:20
 */
@Getter
public class BizException extends RuntimeException {

    private int code;
    private String message;
    private Map<String, Object> errors;

    private BizException() {
    }

    public BizException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BizException(Throwable cause) {
        this(BaseResponseCodeEnum.UNKNOWN.getCode(), BaseResponseCodeEnum.UNKNOWN.getMessage(), cause);
    }

    public BizException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public BizException(int code,
                        String message,
                        Throwable cause,
                        boolean enableSuppression,
                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.message = message;

    }


    public void setErrors(Map<String, Object> errors) {
        this.errors = errors;
    }

}
