package com.lyranxi.link.user.feign;

import com.lyranxi.link.user.bo.user.UserAssignRoleBO;
import com.lyranxi.link.user.bo.user.UserModifyPasswordBO;
import com.lyranxi.link.user.bo.user.UserRegisterBO;
import com.lyranxi.link.user.service.UserService;
import com.lyranxi.link.web.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ranxi
 * @date 2025-04-07 15:44
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserFeignImpl implements UserFeign {

    private final UserService userService;

    @Override
    public Result<Boolean> signup(UserRegisterBO params) {
        return Result.success(userService.signup(params));
    }

    @Override
    public Result<Boolean> resetPassword(String uid) {
        return Result.success(userService.resetPassword(uid));
    }

    @Override
    public Result<Boolean> modifyPassword(UserModifyPasswordBO params) {
        return Result.success(userService.modifyPassword(params));
    }

    @Override
    public Result<Boolean> assignRole(UserAssignRoleBO params) {
        return Result.success(userService.assignRole(params));
    }
}
