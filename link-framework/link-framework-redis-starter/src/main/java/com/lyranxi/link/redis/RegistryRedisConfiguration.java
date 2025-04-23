package com.lyranxi.link.redis;

import com.lyranxi.link.redis.cache.RegistryRedisCacheConfiguration;
import com.lyranxi.link.redis.redisson.RegistryRedissonConfiguration;
import com.lyranxi.link.redis.serial.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author ranxi
 * @date 2025-02-28 18:14
 */
@Slf4j
@Configuration
@AutoConfigureBefore({RedisAutoConfiguration.class})
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
@Import({RegistryRedissonConfiguration.class, RegistryRedisCacheConfiguration.class})
public class RegistryRedisConfiguration {

    @Bean(name = "springSessionDefaultRedisSerialzer")
    public GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer() {
        log.info("registry GenericJackson2JsonRedisSerializer");
        return new GenericJackson2JsonRedisSerializer();
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        // string 序列化
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(RedisSerializer.json());

        // hash 序列化
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(RedisSerializer.json());

        redisTemplate.afterPropertiesSet();
        log.info("registry RedisTemplate<Object,Object>");
        return redisTemplate;
    }

    @Bean
    public JdkSerializationRedisTemplate jdkSerializationRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        log.info("registry JdkSerializationRedisTemplate");
        return new JdkSerializationRedisTemplate(redisConnectionFactory);
    }


    @Bean
    public TimeSecondSerialGenerator timeSecondSerialGenerator(RedisConnectionFactory redisConnectionFactory) {
        log.info("registry TimeSecondSerialGenerator");
        return new TimeSecondSerialGenerator(redisConnectionFactory);
    }

    @Bean
    public TimeMinuteSerialGenerator timeMinuteSerialGenerator(RedisConnectionFactory redisConnectionFactory) {
        log.info("registry TimeMinuteSerialGenerator");
        return new TimeMinuteSerialGenerator(redisConnectionFactory);
    }

    @Bean
    public TimeHourSerialGenerator timeHourSerialGenerator(RedisConnectionFactory redisConnectionFactory) {
        log.info("registry TimeHourSerialGenerator");
        return new TimeHourSerialGenerator(redisConnectionFactory);
    }

    @Bean
    public TimeDaySerialGenerator timeDaySerialGenerator(RedisConnectionFactory redisConnectionFactory) {
        log.info("registry TimeDaySerialGenerator");
        return new TimeDaySerialGenerator(redisConnectionFactory);
    }

    @Bean
    public TimeMonthSerialGenerator timeMonthSerialGenerator(RedisConnectionFactory redisConnectionFactory) {
        log.info("registry TimeMonthSerialGenerator");
        return new TimeMonthSerialGenerator(redisConnectionFactory);
    }


}
