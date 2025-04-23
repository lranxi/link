package com.lyranxi.link.user.feign;

import com.lyranxi.link.common.bo.CoreBO;
import com.lyranxi.link.user.bo.TenantCreateBO;
import com.lyranxi.link.user.constant.UserConstant;
import com.lyranxi.link.web.result.Result;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author ranxi
 * @date 2025-04-23 10:57
 */
@FeignClient(name = UserConstant.SERVICE_NAME, fallback = Object.class)
public interface TenantFeign {

    /**
     * 创建租户
     *
     * @param params 租户信息
     * @return 是否创建成功
     */
    @PostMapping("/api/v1/inner/tenant/create")
    Result<Boolean> create(@RequestBody @Valid CoreBO<TenantCreateBO> params);


}
