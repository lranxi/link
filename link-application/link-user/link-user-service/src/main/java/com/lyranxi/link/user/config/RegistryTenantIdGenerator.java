package com.lyranxi.link.user.config;

import com.lyranxi.link.user.utils.TenantIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ranxi
 * @date 2025-04-15 14:05
 */
@Slf4j
@Configuration
public class RegistryTenantIdGenerator {

    private static final String DICT = "0123456789ABCDEFGHIJKLMNPQRSTUVWXYZ";

    @Bean
    public TenantIdGenerator tenantIdGenerator() {
        return new TenantIdGenerator(DICT);
    }


}
