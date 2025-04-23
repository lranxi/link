package com.lyranxi.link.redis.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * redis 缓存配置
 *
 * @author ranxi
 * @date 2025-03-03 18:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisCacheProperties implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 自动添加服务名称作为前缀,默认false
     */
    private boolean autoAddServiceNamePrefix = false;
    /**
     * 过期时间,默认不过期
     */
    private Duration duration = Duration.ofSeconds(-1);
    /**
     * 缓存过期时间,key为缓存key,value为过期时间,默认不过期
     */
    private Map<String, Duration> cacheDurationMap = new HashMap<>();


}
