package com.lyranxi.link.user.enums;

import com.lyranxi.link.common.exception.BusinessException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ranxi
 * @date 2025-04-14 14:05
 */
@Getter
@RequiredArgsConstructor
public enum RoleScopeEnum {

    TENANT(1, "租户范围"),
    BRAND(2, "品牌范围"),
    STORE(3, "门店范围");

    private final int id;
    private final String name;

    private static final Map<Integer, RoleScopeEnum> CACHE_MAP = new HashMap<>();

    static {
        for (RoleScopeEnum roleScopeEnum : RoleScopeEnum.values()) {
            CACHE_MAP.put(roleScopeEnum.getId(), roleScopeEnum);
        }
    }

    public static RoleScopeEnum getByIdMust(Integer scope) {
        if (!CACHE_MAP.containsKey(scope)) {
            throw new BusinessException("角色权限范围错误");
        }
        return CACHE_MAP.get(scope);
    }

}
