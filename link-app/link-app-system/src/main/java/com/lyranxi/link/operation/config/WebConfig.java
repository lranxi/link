package com.lyranxi.link.operation.config;


import com.lyranxi.link.operation.filter.AuthFilter;
import com.lyranxi.link.operation.handler.AuthenticationHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @author ranxi
 * @date 2025-04-16 11:30
 */
@Slf4j
@Component
public class WebConfig {

    @Bean
    public FilterRegistrationBean<AuthFilter> tokenFilterRegistrationBean(AuthenticationHandler handler) {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthFilter(handler));
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("tokenFilter");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        return registrationBean;
    }

}
