package com.lyranxi.link.redis.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义redis缓存配置
 *
 * @author ranxi
 * @date 2025-03-03 18:09
 */
@Slf4j
@Configuration
@EnableCaching
public class RegistryRedisCacheConfiguration {
    private static final String CACHE_PREFIX = ":cache";
    @Value("${spring.application.name}")
    private String serverName = "unknown";

    @Bean
    @ConditionalOnMissingBean(RedisCacheProperties.class)
    public RedisCacheProperties redisCacheProperties() {
        return new RedisCacheProperties();
    }


    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory, RedisCacheProperties redisCacheProperties) {
        log.info("registry CacheManager");
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(getCacheConfiguration(redisCacheProperties.getDuration(), redisCacheProperties.isAutoAddServiceNamePrefix()))
                .withInitialCacheConfigurations(getCacheConfigurations(redisCacheProperties))
                .transactionAware()
                .build();

    }

    private Map<String, RedisCacheConfiguration> getCacheConfigurations(RedisCacheProperties redisCacheProperties) {
        Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>(16);
        for (Map.Entry<String, Duration> entry : redisCacheProperties.getCacheDurationMap().entrySet()) {
            configurationMap.put(entry.getKey(), this.getCacheConfiguration(entry.getValue(), redisCacheProperties.isAutoAddServiceNamePrefix()));
        }
        return configurationMap;
    }

    private RedisCacheConfiguration getCacheConfiguration(Duration duration, boolean autoAddServiceNamePrefix) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        if (autoAddServiceNamePrefix) {
            redisCacheConfiguration = redisCacheConfiguration.computePrefixWith(getCacheKeyPrefix());
        }
        // 序列化
        redisCacheConfiguration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));
        return redisCacheConfiguration.entryTtl(duration);
    }


    private CacheKeyPrefix getCacheKeyPrefix() {
        return cacheName -> serverName + CACHE_PREFIX + cacheName + ":";
    }

}
