package com.lyranxi.link.redis.redisson;

import com.lyranxi.link.redis.lock.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson 配置
 *
 * @author ranxi
 * @date 2025-03-03 16:28
 */
@Slf4j
@Configuration
@AutoConfigureBefore({RedissonAutoConfiguration.class})
public class RegistryRedissonConfiguration {

    @Bean
    public RedissonClient redissonClient(RedisProperties redisProperties) {
        Config config = new Config();
        config.useSingleServer()
                .setIdleConnectionTimeout(10000)
                .setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort())
                .setPassword(redisProperties.getPassword());

        RedissonClient redissonClient = Redisson.create(config);
        RedissonFactory.redissonClient = redissonClient;
        RedisLock.redissonClient = redissonClient;
        log.info("registry RedissonClient");
        return redissonClient;
    }

}
