package com.lyranxi.link.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author ranxi
 * @date 2025-04-23 18:20
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.lyranxi.link"})
@MapperScan(basePackages = {"com.lyranxi.link.member.mapper"})
public class MemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemberApplication.class);
    }

}
