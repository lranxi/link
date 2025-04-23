package com.lyranxi.link.redis.cache.local;

/**
 * 缓存接口
 *
 * @author ranxi
 * @date 2025-03-04 16:29
 */
public interface LinkCache<K, V> {

    /**
     * 获取缓存数据
     *
     * @param key key
     * @return V 缓存值
     */
    V get(K key);


    /**
     * 保存缓存数据
     *
     * @param key   key
     * @param value value
     */
    void set(K key, V value);

    /**
     * 删除指定缓存
     *
     * @param key key
     * @return 是否删除成功
     */
    Boolean remove(K key);

    /**
     * 刷新有效期
     *
     * @param key key
     * @return 是否成功
     */
    Boolean refreshExpires(K key);
}
