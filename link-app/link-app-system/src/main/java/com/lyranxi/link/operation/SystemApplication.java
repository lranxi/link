package com.lyranxi.link.user;

import com.lyranxi.link.common.enums.PlatformEnum;
import com.lyranxi.link.user.handler.ApiRegisterHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author ranxi
 * @date 2025-04-15 16:44
 */
@EnableFeignClients(basePackages = "com.lyranxi.link")
@EnableDiscoveryClient
@SpringBootApplication
public class SystemApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SystemApplication.class);

        ApiRegisterHandler.register(context, PlatformEnum.OPERATION);
    }

}
