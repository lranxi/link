package com.lyranxi.link.user.feign;

import com.lyranxi.link.user.constant.ServiceName;
import com.lyranxi.link.web.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * user feign
 * @author ranxi
 * @date 2025-03-13 16:00
 */
@FeignClient(contextId = "userFeign",name = ServiceName.USER_SERVICE)
public interface LoginFeign {

    @PostMapping("/api/vi/inner/user/login")
    Result<String> login();

    @PostMapping("/api/v1/inner/user/choice-tenant")
    Result<String> loginWithChoiceTenant();

    @PostMapping("/api/v1/inner/user/logout")
    Result<String> logout();

}
