package com.lyranxi.link.user.feign;

import com.lyranxi.link.user.bo.user.UserRegisterBO;
import com.lyranxi.link.user.constant.ServiceName;
import com.lyranxi.link.web.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 注册feign
 *
 * @author ranxi
 * @date 2025-03-13 18:29
 */
@FeignClient(contextId = "signupFeign", name = ServiceName.USER_SERVICE)
public interface SignupFeign {

    /**
     * 用户注册
     *
     * @param params 注册用户信息
     * @return Result<Boolean> 操作是否成功
     */
    @PostMapping("/api/v1/inner/user/signup")
    Result<Boolean> signup(@RequestBody UserRegisterBO params);


}
