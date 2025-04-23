package com.lyranxi.link.common.exception;

import com.lyranxi.link.common.enums.BaseResponseCodeEnum;

/**
 * @author ranxi
 * @date 2025-03-12 21:02
 */
public class BusinessException extends BizException {

    private static final BaseResponseCodeEnum CODE_ENUM = BaseResponseCodeEnum.BUSINESS_EXCEPTION;

    public BusinessException(String message) {
        super(CODE_ENUM.getCode(), message);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
