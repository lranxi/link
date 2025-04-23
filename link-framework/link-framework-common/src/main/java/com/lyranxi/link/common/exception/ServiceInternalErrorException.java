package com.lyranxi.link.common.exception;

import com.lyranxi.link.common.enums.BaseResponseCodeEnum;

/**
 * @author ranxi
 * @date 2025-04-09 11:52
 */
public class ServiceInternalErrorException extends BizException {

    private static final BaseResponseCodeEnum CODE_ENUM = BaseResponseCodeEnum.SERVER_INTERNAL_ERROR;

    public ServiceInternalErrorException() {
        super(CODE_ENUM.getCode(), CODE_ENUM.getMessage());
    }

    public ServiceInternalErrorException(String message) {
        super(CODE_ENUM.getCode(), message);
    }

    public ServiceInternalErrorException(Throwable cause) {
        super(CODE_ENUM.getCode(), CODE_ENUM.getMessage(), cause);
    }

    public ServiceInternalErrorException(String message, Throwable cause) {
        super(CODE_ENUM.getCode(), message, cause);
    }

}
