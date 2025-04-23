package com.lyranxi.link.web;

import com.lyranxi.link.web.exception.GlobalResultExceptionHandler;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ranxi
 * @date 2025-03-12 18:40
 */
@Slf4j
@Configuration
public class RegistryWebConfiguration implements ApplicationContextAware {

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        ApplicationContextProvider.setContext(applicationContext);
        log.info("registry application context");
    }

    @Bean
    public GlobalResultExceptionHandler globalResultExceptionHandler() {
        log.info("registry GlobalResultExceptionHandler");
        return new GlobalResultExceptionHandler();
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        log.info("registry Jackson2ObjectMapperBuilderCustomizer");
        return new LinkJackson2ObjectMapperBuilderCustomizer();
    }
}
