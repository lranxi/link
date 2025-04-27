package com.lyranxi.link.user.cache;

import com.lyranxi.link.redis.cache.local.AbstractLocalCache;
import com.lyranxi.link.redis.cache.local.LocalCacheProperties;
import com.lyranxi.link.user.feign.ApiFeign;
import com.lyranxi.link.user.vo.ApiVO;
import com.lyranxi.link.web.result.Result;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * 接口缓存 key: method_uri
 *
 * @author ranxi
 * @date 2025-04-16 10:09
 */
@Component
public class ApiCache extends AbstractLocalCache<String, ApiVO> {

    @Resource
    private ApiFeign apiFeign;

    public ApiCache() {
        super(LocalCacheProperties.builder()
                .redisDirectory("user:api:")
                .initialCapacity(64)
                .maximumSize(2000)
                .expireAfterWrite(Duration.ofSeconds(60))
                .expireAfterAccess(Duration.ofSeconds(15))
                .redisExpireAfterSeconds(50 * 60)
                .build());
    }

    @Override
    protected ApiVO getFromDB(String key) {
        String[] spilt = key.split("_");
        Result<ApiVO> result = apiFeign.getByUri(Integer.parseInt(spilt[0]), spilt[1], spilt[2]);
        if (result.success()) {
            return result.getData();
        }
        return null;
    }
}
