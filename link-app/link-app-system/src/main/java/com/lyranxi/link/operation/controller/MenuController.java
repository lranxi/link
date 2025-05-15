package com.lyranxi.link.operation.controller;

import com.lyranxi.link.common.annotation.Api;
import com.lyranxi.link.user.bo.MenuCreateBO;
import com.lyranxi.link.user.bo.MenuUpdateBO;
import com.lyranxi.link.user.feign.MenuFeign;
import com.lyranxi.link.user.http.HttpContext;
import com.lyranxi.link.web.result.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单接口
 *
 * @author ranxi
 * @date 2025-04-23 16:43
 */
@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuFeign menuFeign;

    /**
     * 创建菜单
     *
     * @param params 菜单信息
     * @return 操作结果
     */
    @Api(name = "创建菜单", author = "lyranxi")
    @PostMapping("/create")
    public Result<Boolean> create(@RequestBody @Valid MenuCreateBO params) {
        return menuFeign.create(HttpContext.build(params));
    }

    /**
     * 编辑菜单
     *
     * @param params 菜单信息
     * @return 操作结果
     */
    @Api(name = "编辑菜单", author = "lyranxi")
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody @Valid MenuUpdateBO params) {
        return menuFeign.update(HttpContext.build(params));
    }

}
