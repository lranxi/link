package com.lyranxi.link.redis.serial;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * 按秒生成
 *
 * @author ranxi
 * @date 2025-02-28 19:00
 */
@Slf4j
public class TimeSecondSerialGenerator extends AbstractTimeSerialGenerator {

    public TimeSecondSerialGenerator(RedisConnectionFactory redisConnectionFactory) {
        super(redisConnectionFactory, "t_serial:second:incr:", "yyMMddHHmmss", 60 * 2, 3);
    }

}
