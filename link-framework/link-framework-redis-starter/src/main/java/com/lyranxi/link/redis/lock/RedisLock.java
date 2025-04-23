package com.lyranxi.link.redis.lock;

import cn.hutool.core.util.StrUtil;
import com.lyranxi.link.redis.exception.LinkCacheException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * redis锁 基于redisson实现
 *
 * @author ranxi
 * @date 2025-03-03 16:32
 */
@Slf4j
public class RedisLock {

    public static RedissonClient redissonClient;
    /**
     * 锁前缀
     */
    private static final String LOCK_PREFIX = "lock:";
    /**
     * 锁备注后缀
     */
    private static final String REMARK_SUFFIX = ":remark";
    /**
     * 时间单位,毫秒
     */
    private static final TimeUnit TIME_UNIT_MS = TimeUnit.MILLISECONDS;

    /**
     * 锁最长持有时间(毫秒),到期后自动释放
     */
    private final long leaseTimeMillSeconds;
    /**
     * keys
     */
    private final String[] keys;
    /**
     * 是否为多个锁组合
     */
    private boolean isMultiLock = false;
    private final RLock lock;
    private final String remarkKey;
    private final String remark;

    /**
     * 构造一个非公平锁
     *
     * @param key                  key
     * @param leaseTimeMillSeconds 持有时间
     */
    public RedisLock(String key, long leaseTimeMillSeconds) {
        this(key, leaseTimeMillSeconds, null, false);
    }

    /**
     * 构造一个非公平锁
     *
     * @param key                  key
     * @param leaseTimeMillSeconds 持有时间
     * @param remark               备注
     */
    public RedisLock(String key, long leaseTimeMillSeconds, String remark) {
        this(key, leaseTimeMillSeconds, remark, false);
    }

    /**
     * 构造方法,构造一个RedisLock对象
     *
     * @param key                  key
     * @param leaseTimeMillSeconds 持有时间
     * @param remark               备注信息
     * @param fair                 是否为公平锁
     */
    public RedisLock(String key, long leaseTimeMillSeconds, String remark, boolean fair) {
        this.leaseTimeMillSeconds = leaseTimeMillSeconds;
        this.keys = new String[]{LOCK_PREFIX + key};
        this.remarkKey = LOCK_PREFIX + key + REMARK_SUFFIX;
        if (fair) {
            this.lock = redissonClient.getFairLock(this.keys[0]);
        } else {
            this.lock = redissonClient.getLock(this.keys[0]);
        }
        this.remark = remark;
    }

    /**
     * 构造方法,构造一个联锁对象
     *
     * @param keys                 keys
     * @param leaseTimeMillSeconds 持有时间
     * @param remark               备注信息
     * @param fair                 是否为公平锁
     */
    public RedisLock(String[] keys, long leaseTimeMillSeconds, String remark, boolean fair) {
        this.leaseTimeMillSeconds = leaseTimeMillSeconds;
        RLock[] locks = new RLock[keys.length];
        this.keys = new String[keys.length];
        for (int i = 0; i < keys.length; i++) {
            this.keys[i] = LOCK_PREFIX + keys[i];
            if (fair) {
                locks[i] = redissonClient.getFairLock(this.keys[i]);
            } else {
                locks[i] = redissonClient.getLock(this.keys[i]);
            }
        }
        this.lock = redissonClient.getMultiLock(locks);
        this.isMultiLock = true;
        this.remarkKey = LOCK_PREFIX + UUID.randomUUID() + REMARK_SUFFIX;
        this.remark = remark;
    }


    /**
     * 获取锁
     *
     * @return boolean
     */
    public boolean lock() {
        try {
            lock.lock(leaseTimeMillSeconds, TIME_UNIT_MS);
            // 设置锁描述
            this.setRemark();
            return true;
        } catch (Exception e) {
            log.warn("lock failed. name: {}", this.lock.getName());
            return false;
        }
    }

    /**
     * 获取锁并执行Supplier
     *
     * @param supplier 执行的Supplier
     * @param <T>      处理结果
     * @return 获取到锁后的执行结果, 获取不到锁则抛出异常
     */
    public <T> T lockAndRun(Supplier<T> supplier) {
        if (!this.lock()) {
            this.throwExceptionAfterLockFailed("获取锁失败");
        }
        try {
            return supplier.get();
        } finally {
            if (this.lock.isHeldByCurrentThread()) {
                this.unlock();
            }
        }
    }

    /**
     * 尝试获取锁
     *
     * @param waitTimeMillSeconds 锁等待时间
     * @return boolean 是否获取成功
     */
    public boolean tryLock(long waitTimeMillSeconds) {
        try {
            boolean result = lock.tryLock(waitTimeMillSeconds, leaseTimeMillSeconds, TIME_UNIT_MS);
            if (result) {
                try {
                    this.setRemark();
                } catch (Exception e) {
                    log.warn("set lock remark failed.", e);
                }
            }
            return result;
        } catch (Exception e) {
            log.warn("try lock failed. name: {}, waitTimeMillSeconds: {}, Cause by: {}", this.lock.getName(), waitTimeMillSeconds, e.getMessage());
            return false;
        }
    }

    /**
     * 尝试获取锁,并执行supplier
     *
     * @param waitTimeMillSeconds 锁等待时长
     * @param supplier            supper
     * @param <T>                 处理结果
     * @return T 执行结果
     */
    public <T> T tryLockAndRune(long waitTimeMillSeconds, Supplier<T> supplier) {
        if (!this.tryLock(waitTimeMillSeconds)) {
            this.throwExceptionAfterLockFailed("锁获取超时");
        }
        try {
            return supplier.get();
        } finally {
            if (this.lock.isHeldByCurrentThread()) {
                this.unlock();
            }
        }
    }

    /**
     * 强制释放锁
     */
    public void forceUnlock() {
        try {
            redissonClient.getKeys().delete(this.keys);
        } finally {
            this.deleteRemark();
        }
    }


    /**
     * 释放锁
     */
    public void unlock() {
        try {
            lock.unlock();
        } catch (Exception e) {
            log.error("unlock failed. cause by: {}", e.getMessage());
        } finally {
            this.deleteRemark();
        }
    }


    /**
     * 设置锁描述
     */
    public void setRemark() {
        try {
            if (StrUtil.isNotBlank(this.remark)) {
                RBucket<String> bucket = redissonClient.getBucket(this.remarkKey);
                bucket.set(remark, this.leaseTimeMillSeconds, TIME_UNIT_MS);
            }
        } catch (Exception e) {
            log.warn("设置锁描述异常.", e);
        }
    }

    /**
     * 获取锁描述
     *
     * @return string 锁描述信息,不存在时返回null
     */
    public String getRemark() {
        try {
            if (StrUtil.isBlank(this.remark)) {
                return null;
            }
            RBucket<String> bucket = redissonClient.getBucket(this.remarkKey);
            return bucket.get();
        } catch (Exception e) {
            log.warn("获取锁描述异常", e);
            return null;
        }
    }


    /**
     * 删除锁描述信息
     */
    public void deleteRemark() {
        try {
            if (StrUtil.isNotBlank(this.remark)) {
                RBucket<Object> bucket = redissonClient.getBucket(this.remarkKey);
                bucket.delete();
            }
        } catch (Exception e) {
            log.warn("删除锁描述失败.", e);
        }
    }

    /**
     * 是否为联锁
     *
     * @return boolean
     */
    public boolean isMultiLock() {
        return isMultiLock;
    }


    /**
     * 获取失败后抛出异常
     *
     * @param message 异常信息,如果为空则使用remark
     */
    public void throwExceptionAfterLockFailed(String message) {
        String remark = getRemark();
        if (StrUtil.isBlank(remark)) {
            throw new LinkCacheException(message);
        } else {
            throw new LinkCacheException(remark);
        }
    }

}
