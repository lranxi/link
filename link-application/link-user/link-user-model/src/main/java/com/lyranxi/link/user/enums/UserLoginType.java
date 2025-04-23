package com.lyranxi.link.user.enums;

import com.lyranxi.link.common.util.asserts.AssertBusinessException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录方式
 *
 * @author ranxi
 * @date 2025-04-11 15:41
 */
@Getter
@RequiredArgsConstructor
public enum UserLoginType {

    PASSWORD(1, "密码登录"),

    SMS_CODE(2, "短信验证码"),

    WECHAT(3, "微信扫码登录");

    private final int type;
    private final String name;


    private static final Map<Integer, UserLoginType> CACHE_MAP = new HashMap<>(4);

    static {
        for (UserLoginType userLoginType : UserLoginType.values()) {
            CACHE_MAP.put(userLoginType.getType(), userLoginType);
        }
    }


    public static UserLoginType findByTypeMust(Integer type) {
        UserLoginType enums = CACHE_MAP.get(type);
        AssertBusinessException.nonNull(enums, "登录方式不正确");
        return enums;
    }

}
