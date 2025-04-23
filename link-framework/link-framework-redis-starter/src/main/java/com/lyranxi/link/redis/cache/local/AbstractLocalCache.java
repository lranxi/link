package com.lyranxi.link.redis.cache.local;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.lyranxi.link.redis.JdkSerializationRedisTemplate;
import com.lyranxi.link.redis.exception.LinkCacheException;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author ranxi
 * @date 2025-03-04 16:32
 */
@Slf4j
public abstract class AbstractLocalCache<K, V> {
    @Autowired
    private JdkSerializationRedisTemplate jdkSerializationRedisTemplate;
    private final LocalCacheProperties localCacheProperties;
    private final LoadingCache<K, V> cache;

    public AbstractLocalCache(LocalCacheProperties localCacheProperties) {
        if (Objects.isNull(localCacheProperties)) {
            log.error("本地缓存参数不能为空");
            throw new NullPointerException("本地缓存参数不能为空");
        }

        this.localCacheProperties = localCacheProperties;
        this.cache = Caffeine.newBuilder()
                .maximumSize(localCacheProperties.getMaximumSize())
                .expireAfterWrite(localCacheProperties.getExpireAfterWrite())
                .expireAfterAccess(localCacheProperties.getExpireAfterAccess())
                .build(new CacheLoader<K, V>() {
                    @Override
                    public @Nullable V load(K k) throws Exception {
                        return getFromRedis(k);
                    }
                });
        log.info("初始化缓存: {}", getClass().getName());
    }

    private V getFromRedis(K key) {
        V value;
        try {
            value = (V) jdkSerializationRedisTemplate.get(getRedisKey(key));
        } catch (Exception e) {
            log.warn("get from redis failed. cause by: {}", getRedisKey(key), e);
            throw new LinkCacheException(e.getMessage(), e);
        }

        if (log.isDebugEnabled()) {
            log.debug("get: {} from redis. vlaue: {}", key, value);
        }

        try {
            this.afterGetFromRedis(key, value);
        } catch (Exception e) {
            String message = String.format("afterGetFromRedis failed. key: %s, value: %s", key, value);
            log.warn(message);
            throw new LinkCacheException(message);
        }

        if (value == null) {
            value = getFromDB(key);
            if (value != null) {
                jdkSerializationRedisTemplate.set(getRedisKey(key), value, localCacheProperties.getRedisExpireAfterSeconds(), TimeUnit.SECONDS);
                if (log.isDebugEnabled()) {
                    log.debug("set: {}, value: {} into redis", key, value);
                }
            }
        }
        return value;
    }

    protected abstract V getFromDB(K key);

    protected void afterGetFromRedis(K key, V value) {
    }

    protected String getRedisKey(K key) {
        return localCacheProperties.getRedisDirectory() + key;
    }

    public V get(K key) {
        V value = cache.get(key);
        if (log.isDebugEnabled()) {
            log.debug("get {} from cache. value: {}", key, value);
        }
        return value;
    }

    public void put(K key, V value) {
        jdkSerializationRedisTemplate.set(getRedisKey(key), value, localCacheProperties.getRedisExpireAfterSeconds(), TimeUnit.SECONDS);
        cache.put(key, value);
        if (log.isDebugEnabled()) {
            log.debug("put {}, value: {} into local-cache and redis", key, value);
        }
    }

    public boolean removeFromLocal(K key) {
        cache.invalidate(key);
        if (log.isDebugEnabled()) {
            log.debug("remove local cache: {}", key);
        }
        return true;
    }

    public boolean removeFromRedis(K key) {
        try {
            return jdkSerializationRedisTemplate.delete(getRedisKey(key));
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("remove redis {}", getRedisKey(key));
            }
        }
    }

    public boolean removeFromLocalAndRedis(K key) {
        return this.removeFromLocal(key) && removeFromRedis(key);
    }

    public boolean refreshLocal(K key) {
        cache.refresh(key);
        if (log.isDebugEnabled()) {
            log.debug("refresh local cache {}", key);
        }
        return true;
    }

    public boolean refreshRedisExpires(K key) {
        try {
            return jdkSerializationRedisTemplate.expire(getRedisKey(key), localCacheProperties.getRedisExpireAfterSeconds(), TimeUnit.SECONDS);
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("refresh redis expires {}", getRedisKey(key));
            }
        }
    }


    public boolean refreshLocalAndRedis(K key) {
        return this.refreshLocal(key) && this.refreshRedisExpires(key);
    }

}
