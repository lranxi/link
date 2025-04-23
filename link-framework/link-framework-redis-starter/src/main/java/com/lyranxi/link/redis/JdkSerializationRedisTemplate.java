package com.lyranxi.link.redis;

import lombok.Getter;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * jdk 序列化 RedisTemplate'
 * <p>
 * 使用注意: key value为自定义实体对象时需要实现{@link java.io.Serializable}
 * </p>
 *
 * @author ranxi
 * @date 2025-02-28 18:23
 */
public class JdkSerializationRedisTemplate {

    private static final JdkSerializationRedisSerializer REDIS_SERIALIZER = new JdkSerializationRedisSerializer();
    private static final RedisSerializer<String> KEY_SERIALIZER = new StringRedisSerializer();
    private static final RedisSerializer<Object> VALUE_SERIALIZER = new JdkSerializationRedisSerializer();

    private RedisConnectionFactory redisConnectionFactory;
    @Getter
    private RedisConnection redisConnection;

    private JdkSerializationRedisTemplate() {
    }

    public JdkSerializationRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.redisConnection = redisConnectionFactory.getConnection();
    }

    /********************************** key value 序列化/反序列化 **************************************/
    public byte[] serializeKey(String key) {
        return KEY_SERIALIZER.serialize(key);
    }

    public byte[][] rawKeys(Collection<String> keys) {
        final byte[][] rawKeys = new byte[keys.size()][];

        int i = 0;
        for (String key : keys) {
            rawKeys[i++] = serializeKey(key);
        }
        return rawKeys;
    }

    public byte[] serializeValue(Object value) {
        return VALUE_SERIALIZER.serialize(value);
    }

    public Object deserializeValue(byte[] bytes) {
        return REDIS_SERIALIZER.deserialize(bytes);
    }

    /************************************************************************/

    public <T> T get(String key, Class<T> clazz) {
        return (T) get(key);
    }

    public Object get(String key) {
        byte[] rawKey = serializeKey(key);
        return deserializeValue(redisConnection.stringCommands().get(rawKey));
    }

    public Boolean set(String key, String value) {
        byte[] rawKey = serializeKey(key);
        byte[] rawValue = serializeValue(value);
        return redisConnection.stringCommands().set(rawKey, rawValue);
    }

    public Boolean set(String key, Object value, long timeout, TimeUnit unit) {
        byte[] rawKey = serializeKey(key);
        byte[] rawValue = serializeValue(value);

        try {
            if (TimeUnit.MILLISECONDS.equals(unit)) {
                return redisConnection.stringCommands().pSetEx(rawKey, timeout, rawValue);
            } else {
                return redisConnection.stringCommands().setEx(rawKey, TimeoutUtils.toSeconds(timeout, unit), rawValue);
            }
        } catch (Exception e) {
            return redisConnection.stringCommands().setEx(rawKey, TimeoutUtils.toSeconds(timeout, unit), rawValue);
        }
    }


    public Long increment(String key) {
        byte[] rawKey = serializeKey(key);
        return redisConnection.stringCommands().incr(rawKey);
    }

    public Long decrement(String key) {
        byte[] rawKey = serializeKey(key);
        return redisConnection.stringCommands().decr(rawKey);
    }


    public Boolean expire(String key, final long timeout, final TimeUnit unit) {
        byte[] rawKey = serializeKey(key);
        try {
            if (TimeUnit.MILLISECONDS.equals(unit)) {
                return redisConnection.keyCommands().pExpire(rawKey, timeout);
            } else {
                return redisConnection.keyCommands().expire(rawKey, TimeoutUtils.toSeconds(timeout, unit));
            }
        } catch (Exception e) {
            return redisConnection.keyCommands().expire(rawKey, TimeoutUtils.toSeconds(timeout, unit));
        }
    }


    public Boolean delete(String key) {
        byte[] rawKey = serializeKey(key);
        Long result = redisConnection.keyCommands().del(rawKey);
        return result != null && result > 0;
    }


}
