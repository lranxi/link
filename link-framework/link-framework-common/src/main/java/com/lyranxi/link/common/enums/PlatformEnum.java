package com.lyranxi.link.common.enums;

import com.lyranxi.link.common.exception.BusinessException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 平台
 *
 * @author ranxi
 * @date 2025-04-09 14:51
 */
@Getter
@RequiredArgsConstructor
public enum PlatformEnum {

    OPERATION(1, "运营平台"),
    MERCHANT(2, "代理商后台"),
    AGENT(3, "商户后台"),
    MERCHANT_APP(4, "商户APP"),
    APPLETS(5, "用户端小程序");


    private final int id;
    private final String name;

    private static final PlatformEnum[] CACHE = new PlatformEnum[]{OPERATION, MERCHANT, AGENT, MERCHANT_APP, APPLETS};

    public static PlatformEnum getById(int id) {
        if (id >= 1 && id <= CACHE.length) {
            --id;
            return CACHE[id];
        } else {
            throw new BusinessException(BaseResponseCodeEnum.PARAM_PLATFORM_ERROR.getMessage());
        }
    }

}
