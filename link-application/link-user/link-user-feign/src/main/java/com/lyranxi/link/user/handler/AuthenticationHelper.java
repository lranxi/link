package com.lyranxi.link.user.handler;

import com.lyranxi.link.user.token.UserToken;
import com.lyranxi.link.user.vo.ApiVO;

/**
 * @author ranxi
 * @date 2025-04-15 17:09
 */
public interface AuthenticationHelper {

    /**
     * 获取api vo
     *
     * @param uri      请求路径
     * @param method   请求方法
     * @param platform 平台
     * @return ApiVO api vo
     */
    ApiVO getApi(String uri, String method, Integer platform);

    /**
     * 获取员工token内容
     *
     * @param token token信息
     * @return UserToken 员工token内容
     */
    UserToken getUserToken(String token);

    /**
     * 刷新token有效期
     *
     * @param token token
     */
    void refreshTokenExpires(String token);
}
