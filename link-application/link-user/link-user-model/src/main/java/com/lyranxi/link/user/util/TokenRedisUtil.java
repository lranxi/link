package com.lyranxi.link.user.util;

/**
 * @author ranxi
 * @date 2025-04-16 10:43
 */
public class TokenRedisUtil {

    public static final long EXPIRE_SECONDS = 60 * 60 * 72;
    public static final String PREFIX = "user:token:";

    private TokenRedisUtil() {
    }

    public static String getKey(String token) {
        return PREFIX + token;
    }

}
