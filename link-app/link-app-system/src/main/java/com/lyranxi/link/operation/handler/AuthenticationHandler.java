package com.lyranxi.link.operation.handler;


import com.lyranxi.link.operation.cache.ApiCache;
import com.lyranxi.link.redis.JdkSerializationRedisTemplate;
import com.lyranxi.link.user.handler.AuthenticationHelper;
import com.lyranxi.link.user.token.UserToken;
import com.lyranxi.link.user.util.TokenRedisUtil;
import com.lyranxi.link.user.vo.ApiVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author ranxi
 * @date 2025-04-16 10:08
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationHandler implements AuthenticationHelper {

    private final JdkSerializationRedisTemplate jdkSerializationRedisTemplate;
    private final ApiCache apiCache;

    @Override
    public ApiVO getApi(String uri, String method, Integer platform) {
        return apiCache.get(platform + "_" + method + "_" + uri);
    }

    @Override
    public UserToken getUserToken(String token) {
        return jdkSerializationRedisTemplate.get(TokenRedisUtil.getKey(token), UserToken.class);
    }

    @Override
    public void refreshTokenExpires(String token) {
        jdkSerializationRedisTemplate.expire(
                TokenRedisUtil.getKey(token),
                TokenRedisUtil.EXPIRE_SECONDS,
                TimeUnit.SECONDS);
    }
}
