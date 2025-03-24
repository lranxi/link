package com.lyranxi.link.user.redis;

import com.lyranxi.link.redis.JdkSerializationRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author ranxi
 * @date 2025-03-13 17:00
 */
@Slf4j
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    @Autowired
    private JdkSerializationRedisTemplate jdkSerializationRedisTemplate;

    @Test
    public void test(){
        redisTemplate.opsForValue().set("key","value",1200, TimeUnit.SECONDS);
        jdkSerializationRedisTemplate.set("key1","value2",1200,TimeUnit.SECONDS);
    }

}
