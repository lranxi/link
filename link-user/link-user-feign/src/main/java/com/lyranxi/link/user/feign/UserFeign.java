package com.lyranxi.link.user.feign;

import com.lyranxi.link.user.bo.user.UserAssignRoleBO;
import com.lyranxi.link.user.bo.user.UserModifyPasswordBO;
import com.lyranxi.link.user.bo.user.UserRegisterBO;
import com.lyranxi.link.user.constant.ServiceName;
import com.lyranxi.link.web.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 注册feign
 *
 * @author ranxi
 * @date 2025-03-13 18:29
 */
@FeignClient(contextId = "signupFeign", name = ServiceName.USER_SERVICE)
public interface UserFeign {

    /**
     * 用户注册
     *
     * @param params 注册用户信息
     * @return Result<Boolean> 操作是否成功
     */
    @PostMapping("/api/v1/inner/user/signup")
    Result<Boolean> signup(@RequestBody UserRegisterBO params);


    /**
     * 重置用户密码
     *
     * @param uid 用户ID
     * @return 操作是否成功
     */
    @GetMapping("/api/v1/inner/user/reset-password")
    Result<Boolean> resetPassword(@RequestParam("uid") String uid);


    /**
     * 修改密码
     *
     * @param params uid、新密码、旧密码
     * @return 操作结果
     */
    @PostMapping("/api/v1/inner/user/modify-password")
    Result<Boolean> modifyPassword(@RequestBody UserModifyPasswordBO params);

    /**
     * 分配角色
     *
     * @param params uid、roleId
     * @return 操作结果
     */
    @PostMapping("/api/v1/inner/user/assign-role")
    Result<Boolean> assignRole(@RequestBody UserAssignRoleBO params);
}
