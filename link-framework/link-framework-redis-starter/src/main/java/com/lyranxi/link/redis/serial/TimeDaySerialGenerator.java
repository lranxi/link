package com.lyranxi.link.redis.serial;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * 按天生成序列号
 *
 * @author ranxi
 * @date 2025-02-28 18:55
 */
@Slf4j
public class TimeDaySerialGenerator extends AbstractTimeSerialGenerator {

    public TimeDaySerialGenerator(RedisConnectionFactory redisConnectionFactory) {
        super(redisConnectionFactory, "t_serial:day:incr:", "yyMMdd", 60 * 60 * 25, 5);
    }

}
