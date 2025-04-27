package com.lyranxi.link.applets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 小程序接口服务启动类
 *
 * @author ranxi
 * @date 2025-04-27 15:32
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.lyranxi.link"})
public class AppletsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppletsApplication.class, args);
    }

}
