package com.lyranxi.link.operation.controller;

import com.lyranxi.link.common.annotation.Api;
import com.lyranxi.link.user.bo.TenantCreateBO;
import com.lyranxi.link.user.feign.TenantFeign;
import com.lyranxi.link.user.http.HttpContext;
import com.lyranxi.link.web.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 租户接口
 * @author ranxi
 * @date 2025-04-23 16:36
 */
@RestController
@RequestMapping("/tenant")
@RequiredArgsConstructor
public class TenantController {

    private final TenantFeign tenantFeign;

    /**
     * 创建租户
     *
     * @param params 租户信息
     * @return 是否创建成功
     */
    @Api(name = "创建租户", author = " lyranxi")
    @PostMapping("/create")
    public Result<Boolean> create(TenantCreateBO params) {
        return tenantFeign.create(HttpContext.build(params));
    }

}
