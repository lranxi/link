package com.lyranxi.link.common.enums;

import lombok.Getter;

/**
 * 租户类型
 *
 * @author ranxi
 * @date 2025-04-08 18:54
 */
@Getter
public enum TenantTypeEnum {
    GROUP(1, "集团"),
    STORE(2, "门店"),
    OPERATION(3, "运营后台"),
    AGENT(4, "代理商");

    private final Integer type;
    private final String name;
    private static final TenantTypeEnum[] CACHE = new TenantTypeEnum[]{GROUP, STORE, OPERATION, AGENT};

    TenantTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static TenantTypeEnum getByType(int type) {
        if (type >= 1 && type <= 4) {
            --type;
            return CACHE[type];
        } else {
            return null;
        }
    }
}
