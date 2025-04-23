package com.lyranxi.link.redis.unrepatable;

import java.lang.annotation.*;

/**
 * 不可重复的
 *
 * @author ranxi
 * @date 2025-03-04 15:23
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Unrepeatable {

    /**
     * key 支持spEL表达式
     */
    String key() default "";

    /**
     * redis保存的key前缀
     */
    String prefix() default "";

    /**
     * 等待时间, 单位毫秒, 默认0
     * 超时后抛出异常
     */
    long waitTime() default 0;

    /**
     * 租赁时间, 单位毫秒, 默认30秒,
     * 超时后自动释放锁
     */
    long leaseTime() default 0;

    /**
     * 自动释放
     * <p> true: 业务处理完毕后自动释放锁 <p/>
     * <p> false: 业务处理完成不释放锁,等待超过leaseTime后释放</p>
     */
    boolean autoUnlock() default true;

    /**
     * 锁描述, 其他线程通过remark字段可以获取到持有锁的描述信息
     * 支持spEL表达式
     */
    String remark() default "";

}
