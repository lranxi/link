package com.lyranxi.link.redis.serial;

import com.lyranxi.link.redis.exception.LinkCacheException;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 时间序列生成器
 *
 * @author ranxi
 * @date 2025-02-28 18:40
 */
@Slf4j
public class AbstractTimeSerialGenerator {

    private static final String STUFFING = "000000000000000000";
    private static final long[] SIZE_TABLE = {0L, 9L, 99L, 999L, 9999L, 9_9999L,
            99_9999L, 999_9999L, 9999_9999L, 9_9999_9999L, 99_9999_9999L,
            999_9999_9999L, 9999_9999_9999L, 9_9999_9999_9999L, 99_9999_9999_9999L, 999_9999_9999_9999L,
            9999_9999_9999_9999L, 9_9999_9999_9999_9999L, 9_9999_9999_9999_9999L};

    private static final int MAX_SERIAL_LEN = SIZE_TABLE.length - 1;
    private final RedisConnectionFactory redisConnectionFactory;

    /**
     * redis key 前缀
     */
    private final String prefix;
    /**
     * 时间格式
     */
    private final String timePattern;
    private final DateTimeFormatter formatter;
    /**
     * 过期时间
     */
    private final long expireSeconds;
    /**
     * 生成值的最大步进树, 随机值
     */
    private final int maxStep;
    private final Random random = new Random();

    /**
     * 构造方法
     *
     * @param redisConnectionFactory redis连接工厂
     * @param prefix                 redis key 前缀
     * @param timePattern            转换日期格式
     * @param expiresSeconds         key过期时间
     * @author ranxi
     * @date 2025-02-28 18:40
     **/
    public AbstractTimeSerialGenerator(RedisConnectionFactory redisConnectionFactory,
                                       String prefix,
                                       String timePattern,
                                       int expiresSeconds) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.prefix = prefix;
        this.timePattern = timePattern;
        this.formatter = DateTimeFormatter.ofPattern(timePattern);
        this.expireSeconds = expiresSeconds;
        this.maxStep = 1;
    }

    /**
     * 构造方法
     *
     * @param redisConnectionFactory redis连接工厂
     * @param prefix                 redis key 前缀
     * @param timePattern            转换日期格式
     * @param expiresSeconds         key过期时间
     * @param maxStep                生成值的最大步进数, 取随机值, 如果此值为5, 步进则取1-5之间的随机数
     * @author ranxi
     * @date 2025-02-28 18:40
     **/
    public AbstractTimeSerialGenerator(RedisConnectionFactory redisConnectionFactory,
                                       String prefix,
                                       String timePattern,
                                       int expiresSeconds,
                                       int maxStep) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.prefix = prefix;
        this.timePattern = timePattern;
        this.formatter = DateTimeFormatter.ofPattern(timePattern);
        this.expireSeconds = expiresSeconds;
        this.maxStep = maxStep;
    }


    public synchronized String generate(@Nonnull String key, int length) {
        if (length < getMinSerialLength() || length > getMaxSerialLength()) {
            throw new LinkCacheException("invalid length[" + length + "]");
        }
        String dateTime = getStringDateTime();
        RedisAtomicLong counter = new RedisAtomicLong(getRedisKey(key, dateTime), redisConnectionFactory);
        counter.expire(expireSeconds, TimeUnit.SECONDS);

        long num = counter.addAndGet(getStep());
        if (num == 0) {
            num = counter.addAndGet(getStep());
        }

        if (num > SIZE_TABLE[length - timePattern.length()]) {
            if (log.isDebugEnabled()) {
                log.debug("当前时段{}名额已分配完毕,等待下一个时段", dateTime);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return generate(key, length);
        }
        return dateTime + leftFillZero(num, length);
    }


    /**
     * 生成最短序列
     *
     * @return int 最短序列长度
     */
    public int getMinSerialLength() {
        return timePattern.length() + 1;
    }

    /**
     * 生成最长序列长度
     *
     * @return int 最长序列长度
     */
    public int getMaxSerialLength() {
        return timePattern.length() + 1 + MAX_SERIAL_LEN;
    }

    /**
     * 获取步进数
     *
     * @return int 步进数
     */
    public long getStep() {
        if (maxStep == 1) {
            return 1L;
        }
        return random.nextInt(maxStep) + 1;
    }

    private String getRedisKey(String key, String dateTime) {
        return prefix + key + dateTime;
    }

    private String leftFillZero(long num, int length) {
        String s = STUFFING + num;
        return s.substring(s.length() - (length - timePattern.length()));
    }

    private String getStringDateTime() {
        return formatter.format(LocalDateTime.now());
    }

}
