package com.lyranxi.link.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ranxi
 * @date 2025-03-13 16:11
 */
@RestController
public class HealthCheckController {

    /**
     * 健康检查
     *
     * @return String "OK"
     */
    @GetMapping("/health/check")
    public String check() {
        return "OK";
    }

}
