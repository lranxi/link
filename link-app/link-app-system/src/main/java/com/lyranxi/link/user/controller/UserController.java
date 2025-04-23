package com.lyranxi.link.user.controller;

import com.lyranxi.link.common.annotation.Api;
import com.lyranxi.link.common.constant.GlobalConstant;
import com.lyranxi.link.user.bo.UserAssignRoleBO;
import com.lyranxi.link.user.bo.UserCreateBO;
import com.lyranxi.link.user.bo.UserLoginBO;
import com.lyranxi.link.user.bo.UserUpdateBO;
import com.lyranxi.link.user.feign.UserFeign;
import com.lyranxi.link.user.feign.UserLoginFeign;
import com.lyranxi.link.user.handler.AuthorizationUtil;
import com.lyranxi.link.user.http.HttpContext;
import com.lyranxi.link.user.vo.LoginVO;
import com.lyranxi.link.user.vo.MenuTreeSimpleVO;
import com.lyranxi.link.web.result.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户接口
 *
 * @author ranxi
 * @date 2025-04-16 18:17
 */

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserFeign userFeign;
    private final UserLoginFeign userLoginFeign;

    /**
     * 用户登录
     *
     * @param params 账号密码
     * @return token
     */
    @Api(name = "登录", whitelist = true, author = "lyranxi")
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody @Valid UserLoginBO params) {
        return userLoginFeign.loginByPassword(params);
    }

    @Api(name = "查询登录用户菜单信息", author = "lyranxi")
    @GetMapping("/menu/tree")
    public Result<List<MenuTreeSimpleVO>> menuTree() {
        String authorization = HttpContext.Header.getString(GlobalConstant.AUTHORIZATION);
        String token = AuthorizationUtil.parseToken(authorization);
        return userLoginFeign.getMenuTree(token);
    }


    /**
     * 创建用户
     *
     * @param params 用户信息
     * @return 处理结果
     */
    @Api(name = "创建用户", author = "lyranxi")
    @PostMapping("/create")
    public Result<Boolean> create(@RequestBody @Valid UserCreateBO params) {
        return userFeign.create(HttpContext.build(params));
    }

    /**
     * 更新用户信息
     *
     * @param params 用户信息
     * @return 处理结果
     */
    @Api(name = "更新用户信息", author = "lyranxi")
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody @Valid UserUpdateBO params) {
        return userFeign.update(HttpContext.build(params));
    }

    /**
     * 删除用户
     *
     * @param userId 用户id
     * @return 处理结果
     */
    @Api(name = "删除用户", author = "lyranxi")
    @GetMapping("/delete")
    public Result<Boolean> delete(@RequestParam("userId") Long userId) {
        return userFeign.delete(HttpContext.build(userId));
    }

    /**
     * 修改用户状态
     *
     * @param userId 用户ID
     * @return 处理结果
     */
    @Api(name = "修改状态", author = "lyranxi")
    @GetMapping("/switch-enable")
    public Result<Boolean> switchEnable(@RequestParam("userId") Long userId) {
        return userFeign.switchEnable(HttpContext.build(userId));
    }

    /**
     * 重置密码
     *
     * @param userId 用户ID
     * @return 处理结果
     */
    @Api(name = "充值密码", author = "lyranxi")
    @GetMapping("/reset-password")
    public Result<Boolean> resetPassword(@RequestParam("userId") Long userId) {
        return userFeign.restPassword(HttpContext.build(userId));
    }

    /**
     * 分配角色
     *
     * @param params 用户id、角色id
     * @return 操作结果
     */
    @Api(name = "分配角色", author = "lyranxi")
    @PostMapping("/assign-role")
    public Result<Boolean> assignRole(@RequestBody @Valid UserAssignRoleBO params) {
        return userFeign.assignRole(HttpContext.build(params));
    }

}
