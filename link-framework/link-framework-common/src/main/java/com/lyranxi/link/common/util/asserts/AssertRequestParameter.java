package com.lyranxi.link.common.util.asserts;

import com.lyranxi.link.common.enums.BaseResponseCodeEnum;
import com.lyranxi.link.common.exception.BizException;

import java.util.Objects;

/**
 * @author ranxi
 * @date 2025-04-15 11:39
 */
public class AssertRequestParameter {

    private static final BaseResponseCodeEnum CODE_ENUM = BaseResponseCodeEnum.PARAM_FORMAT_ERROR;


    public static void nonNull(Object obj, String message) {
        if (Objects.isNull(obj)) {
            throw new BizException(CODE_ENUM.getCode(), message);
        }
    }

    public static void notBlank(String str, String message) {
        if (str != null && !str.isEmpty()) {
            throw new BizException(CODE_ENUM.getCode(), message);
        }
    }

    public static void isTrue(Boolean condition, String message) {
        if (!condition) {
            throw new BizException(CODE_ENUM.getCode(), message);
        }
    }

}
