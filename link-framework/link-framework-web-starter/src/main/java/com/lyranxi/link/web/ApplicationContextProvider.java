package com.lyranxi.link.web;

import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * 应用上下文提供者
 *
 * @author ranxi
 * @date 2025-03-12 15:40
 */
public class ApplicationContextProvider {
    private static ApplicationContext applicationContext;

    static void setContext(ApplicationContext context) {
        ApplicationContextProvider.applicationContext = context;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 根据class获取bean
     *
     * @param clazz clazz
     * @return 容器中的对象
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 根据名称获取bean
     *
     * @param beanName beanName
     * @return 容器中的对象
     */
    public static <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }


    /**
     * 根据类型获取bean
     * @param clazz class
     * @return T 容器中的对象
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return getApplicationContext().getBeansOfType(clazz);
    }

    /**
     * 根据名称和类型获取bean
     * @param name 名称
     * @param clazz 类型
     * @return 容器中的对象
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    /**
     * 获取属性
     * @param key key
     * @return String 值
     */
    public static String getEnvironmentProperty(String key) {
        return getApplicationContext().getEnvironment().getProperty(key);
    }

    /**
     * 获取激活的配置配置文件
     * @return String 环境
     */
    public static String getActiveProfile() {
        return getApplicationContext().getEnvironment().getActiveProfiles()[0];
    }

}
