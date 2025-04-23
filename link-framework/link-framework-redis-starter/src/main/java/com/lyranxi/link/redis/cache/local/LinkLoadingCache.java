package com.lyranxi.link.redis.cache.local;

import com.lyranxi.link.redis.exception.LinkCacheException;

/**
 * @author ranxi
 * @date 2025-03-04 16:33
 */

public interface LinkLoadingCache<K, V> extends LinkCache<K, V> {

    /**
     * 读取缓存, 如果缓存数据不存在,则抛出异常
     *
     * @param key key
     * @return V 缓存数据
     * @throws LinkCacheException 缓存数据不存在时抛出异常
     */
    @Override
    V get(K key) throws LinkCacheException;

}
