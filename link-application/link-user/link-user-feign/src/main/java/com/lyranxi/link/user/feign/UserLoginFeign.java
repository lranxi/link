package com.lyranxi.link.user.feign;

import com.lyranxi.link.user.bo.UserLoginBO;
import com.lyranxi.link.user.constant.UserConstant;
import com.lyranxi.link.user.vo.LoginVO;
import com.lyranxi.link.user.vo.MenuTreeSimpleVO;
import com.lyranxi.link.web.result.Result;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author ranxi
 * @date 2025-04-11 16:06
 */
@FeignClient(name = UserConstant.SERVICE_NAME, fallback = Object.class)
public interface UserLoginFeign {

    /**
     * 用户账号密码登录
     *
     * @param params 账号密码
     * @return Result<LoginVO> token信息
     */
    @PostMapping("/api/v1/inner/user/login/password")
    Result<LoginVO> loginByPassword(@RequestBody @Valid UserLoginBO params);


    /**
     * 退出登录
     *
     * @param token token信息
     * @return 操作结果
     */
    @PostMapping("logout")
    Result<Boolean> logout(@RequestParam("token") String token);

    /**
     * 查询登录用户的菜单树
     *
     * @param token token
     * @return Result<List < MenuTreeSimpleVO>> 菜单树
     */
    @GetMapping("/api/v1/inner/user/token/menu/tree")
    Result<List<MenuTreeSimpleVO>> getMenuTree(@RequestParam("token") String token);

}
