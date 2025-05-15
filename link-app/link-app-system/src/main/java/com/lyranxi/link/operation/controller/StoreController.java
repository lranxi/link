package com.lyranxi.link.operation.controller;

import com.lyranxi.link.common.annotation.Api;
import com.lyranxi.link.user.bo.StoreCreateBO;
import com.lyranxi.link.user.feign.StoreFeign;
import com.lyranxi.link.user.http.HttpContext;
import com.lyranxi.link.web.result.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 门店接口
 *
 * @author ranxi
 * @date 2025-04-23 16:41
 */
@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreFeign storeFeign;

    @Api(name = "创建门店", author = "lyranxi")
    @PostMapping("/create")
    public Result<Boolean> create(@RequestBody @Valid StoreCreateBO params) {
        return storeFeign.create(HttpContext.build(params));
    }

}
