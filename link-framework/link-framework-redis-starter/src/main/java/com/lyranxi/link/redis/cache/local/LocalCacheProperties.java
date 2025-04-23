package com.lyranxi.link.redis.cache.local;

import jakarta.annotation.Nonnull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.time.Duration;

/**
 * 本地缓存配置
 *
 * @author ranxi
 * @date 2025-03-04 16:35
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class LocalCacheProperties {
    /** 缓存目录 */
    private String redisDirectory = "local-cache:";
    /** 初始化容量 */
    private long initialCapacity = 16;
    /** 最大容量 */
    private long maximumSize = 1024;

    /** 最近一次访问后,间隔多长时间未再次访问就使数据过期 */
    @Nonnull
    private Duration expireAfterAccess;

    /** 数据见多多久没有更新就使数据过期 */
    @NonNull
    private Duration expireAfterWrite;

    private long redisExpireAfterSeconds = 300;

}
