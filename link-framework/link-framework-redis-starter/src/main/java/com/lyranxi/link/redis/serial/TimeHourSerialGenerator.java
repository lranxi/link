package com.lyranxi.link.redis.serial;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * 按小时生成序列号
 *
 * @author ranxi
 * @date 2025-02-28 18:57
 */
@Slf4j
public class TimeHourSerialGenerator extends AbstractTimeSerialGenerator {

    public TimeHourSerialGenerator(RedisConnectionFactory redisConnectionFactory) {
        super(redisConnectionFactory, "t_serial:hour:incr:", "yyyyMMddHH", 60 * 60 * 2, 5);
    }

}
