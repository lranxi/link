package com.lyranxi.link.mysql;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus
 * @author ranxi
 * @date 2025-03-04 18:56
 */
@Slf4j
@Configuration
public class AutoRegistryMybatisPlusConfiguration {

    @Bean
    public MybatisPlusInterceptor interceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        log.info("registry Mybatis-Plus Interceptor(PaginationInnerInterceptor)");
        return interceptor;
    }

}
