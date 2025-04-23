package com.lyranxi.link.user.controller;

import com.lyranxi.link.common.annotation.Api;
import com.lyranxi.link.user.bo.BrandCreateBO;
import com.lyranxi.link.user.feign.BrandFeign;
import com.lyranxi.link.user.http.HttpContext;
import com.lyranxi.link.web.result.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 品牌接口
 *
 * @author ranxi
 * @date 2025-04-23 16:39
 */
@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandController {

    private final BrandFeign brandFeign;

    /**
     * 创建品牌
     *
     * @param params 品牌信息
     * @return 是否创建成功
     */
    @Api(name = "创建品牌", author = "lyranxi")
    @PostMapping("/create")
    public Result<Boolean> create(@RequestBody @Valid BrandCreateBO params) {
        return brandFeign.create(HttpContext.build(params));
    }

}
