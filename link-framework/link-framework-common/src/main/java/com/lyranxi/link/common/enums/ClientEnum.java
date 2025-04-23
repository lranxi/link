package com.lyranxi.link.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author ranxi
 * @date 2025-04-16 10:56
 */
@Getter
@RequiredArgsConstructor
public enum ClientEnum {

    OPERATION(1, "运营后台", PlatformEnum.OPERATION),
    MERCHANT(2, "商家后台", PlatformEnum.MERCHANT),
    AGENT(3, "代理商后台", PlatformEnum.AGENT),
    APP_IOS(4, "IosApp", PlatformEnum.MERCHANT_APP),
    APP_ANDROID(5, "安卓App", PlatformEnum.MERCHANT_APP),
    APPLET_WECHAT(6, "微信小程序", PlatformEnum.APPLETS),
    APPLET_ALI(7, "阿里小程序", PlatformEnum.APPLETS),
    APPLET_TIKTOK(8, "抖音小程序", PlatformEnum.APPLETS);

    private final int id;
    private final String name;
    private final PlatformEnum platform;

    private static final Map<Integer, ClientEnum> CACHE_MAP = new HashMap<>(8);

    static {
        for (ClientEnum clientEnum : ClientEnum.values()) {
            CACHE_MAP.put(clientEnum.getId(), clientEnum);
        }
    }


    public static ClientEnum findByIdMust(Integer id) {
        ClientEnum clientEnum = CACHE_MAP.get(id);
        if (Objects.isNull(clientEnum)) {
            throw new IllegalArgumentException("客户端ID不合法");
        }
        return clientEnum;
    }

}
