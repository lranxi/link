package com.lyranxi.link.user.controller;

import com.lyranxi.link.common.annotation.Api;
import com.lyranxi.link.user.feign.ApiFeign;
import com.lyranxi.link.user.vo.ApiVO;
import com.lyranxi.link.web.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * api接口
 * @author ranxi
 * @date 2025-04-16 16:29
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {
    private final ApiFeign apiFeign;

    /**
     * 查询api信息
     *
     * @param scope  适用范围
     * @param method 请求方法
     * @param uri    uri
     * @return Result<ApiVO> api信息
     */
    @Api(name = "查询api信息",author = "lyranxi")
    @GetMapping("/uri")
    public Result<ApiVO> getByUri(Integer scope, String method, String uri) {
        return apiFeign.getByUri(scope, method, uri);
    }


}
