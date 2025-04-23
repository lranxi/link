package com.lyranxi.link.redis.redisson;

import lombok.Getter;
import org.redisson.api.RedissonClient;

/**
 * redisson工厂 提供redissonClient实例
 * @author ranxi
 * @date 2025-03-03 16:31
 */
public class RedissonFactory {

    @Getter
    static RedissonClient redissonClient;

}
