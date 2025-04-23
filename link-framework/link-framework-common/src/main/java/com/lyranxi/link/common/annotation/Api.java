package com.lyranxi.link.common.annotation;

import java.lang.annotation.*;

/**
 * api 接口信息,应用启动时会扫描项目，获取所有接口信息
 *
 * @author ranxi
 * @date 2025-03-24 19:35
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Api {

    /**
     * 接口名称
     */
    String name() default "";

    /**
     * 是否有效
     */
    boolean valid() default true;

    /**
     * 是否白名单,白名单不验证token
     */
    boolean whitelist() default false;

    /**
     * 接口负责人
     */
    String author() default "";

}
