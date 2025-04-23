package com.lyranxi.link.redis.serial;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * 按分钟生成
 *
 * @author ranxi
 * @date 2025-02-28 18:58
 */
@Slf4j
public class TimeMinuteSerialGenerator extends AbstractTimeSerialGenerator {

    public TimeMinuteSerialGenerator(RedisConnectionFactory redisConnectionFactory) {
        super(redisConnectionFactory, "t_serial:minute:incr:", "yyyyMMddHHmm", 60 * 10, 3);
    }

}
