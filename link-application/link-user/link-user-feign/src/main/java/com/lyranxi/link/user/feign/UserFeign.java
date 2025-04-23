package com.lyranxi.link.user.feign;

import com.lyranxi.link.common.bo.CoreBO;
import com.lyranxi.link.user.bo.UserAssignRoleBO;
import com.lyranxi.link.user.bo.UserCreateBO;
import com.lyranxi.link.user.bo.UserUpdateBO;
import com.lyranxi.link.user.constant.UserConstant;
import com.lyranxi.link.web.result.Result;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author ranxi
 * @date 2025-04-15 11:25
 */
@FeignClient(name = UserConstant.SERVICE_NAME, fallback = Object.class)
public interface UserFeign {

    /**
     * 创建用户
     *
     * @param params 用户信息
     * @return 是否成功
     */
    @PostMapping("/api/v1/inner/user/create")
    Result<Boolean> create(@RequestBody @Valid CoreBO<UserCreateBO> params);

    /**
     * 编辑用户信息
     *
     * @param params 用户信息
     * @return 是否成功
     */
    @PostMapping("/api/v1/inner/user/update")
    Result<Boolean> update(@RequestBody @Valid CoreBO<UserUpdateBO> params);

    /**
     * 删除用户
     *
     * @param params 用户id
     * @return 是否成功
     */
    @PostMapping("/api/v1/inner/user/delete")
    Result<Boolean> delete(@RequestBody @Valid CoreBO<Long> params);

    /**
     * 切换停用启用状态
     *
     * @param params 用户id
     * @return 是否成功
     */
    @PostMapping("/api/v1/inner/user/switch-enable")
    Result<Boolean> switchEnable(@RequestBody @Valid CoreBO<Long> params);

    /**
     * 用户重置密码
     *
     * @param params 用户id
     * @return 操作结果
     */
    @PostMapping("/api/v1/inner/user/reset-password")
    Result<Boolean> restPassword(CoreBO<Long> params);

    /**
     * 分配角色
     *
     * @param params 用户ID、角色ID
     * @return 操作结果
     */
    @PostMapping("/api/v1/inner/user/assign-role")
    Result<Boolean> assignRole(CoreBO<UserAssignRoleBO> params);
}
