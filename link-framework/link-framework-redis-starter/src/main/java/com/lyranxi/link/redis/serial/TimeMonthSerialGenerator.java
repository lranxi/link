package com.lyranxi.link.redis.serial;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * 按月生成
 *
 * @author ranxi
 * @date 2025-02-28 18:59
 */
@Slf4j
public class TimeMonthSerialGenerator extends AbstractTimeSerialGenerator {

    public TimeMonthSerialGenerator(RedisConnectionFactory redisConnectionFactory) {
        super(redisConnectionFactory, "t_serial:month:incr:", "yyMM", 60 * 60 * 24 * 30, 3);
    }

}
