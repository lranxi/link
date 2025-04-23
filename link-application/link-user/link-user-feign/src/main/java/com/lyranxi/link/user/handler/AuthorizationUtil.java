package com.lyranxi.link.user.handler;

import cn.hutool.core.util.StrUtil;
import com.lyranxi.link.common.constant.GlobalConstant;

/**
 * Authorization 工具
 *
 * @author ranxi
 * @date 2025-04-15 17:02
 */
public class AuthorizationUtil {

    private AuthorizationUtil() {
    }

    /**
     * 解析授权信息获取token
     * @param token 授权信息
     * @return String token
     */
    public static String parseToken(String token) {
        if (StrUtil.isBlank(token)) {
            return null;
        }
        if (!token.startsWith(GlobalConstant.AUTHORIZATION_BEARER)
                || token.length() <= GlobalConstant.AUTHORIZATION_BEARER.length()) {
            return null;
        }
        return token.substring(GlobalConstant.AUTHORIZATION_BEARER.length());
    }

}
