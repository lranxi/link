package com.lyranxi.link.redis.exception;

import com.lyranxi.link.common.enums.BaseResponseCodeEnum;
import com.lyranxi.link.common.exception.BizException;

/**
 * 缓存异常
 *
 * @author lyranxi
 * @date 2025-02-28 17:48
 */
public class LinkCacheException extends BizException {

    private static final BaseResponseCodeEnum CODE = BaseResponseCodeEnum.CACHE_ERROR;

    public LinkCacheException() {
        super(CODE.getCode(), CODE.getMessage());
    }

    public LinkCacheException(String message) {
        super(CODE.getCode(), CODE.getMessage() + ":" + message);
    }

    public LinkCacheException(Throwable cause) {
        super(CODE.getCode(), CODE.getMessage(), cause);
    }

    public LinkCacheException(String message, Throwable cause) {
        super(CODE.getCode(), message, cause);
    }

    public LinkCacheException(BaseResponseCodeEnum codeEnum) {
        super(codeEnum.getCode(), codeEnum.getMessage());
    }

    public LinkCacheException(int code, String message) {
        super(code, message);
    }

}
