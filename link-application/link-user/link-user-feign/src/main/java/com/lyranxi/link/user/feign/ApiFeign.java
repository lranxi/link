package com.lyranxi.link.user.feign;

import com.lyranxi.link.user.bo.ApiCreateBO;
import com.lyranxi.link.user.bo.ApiSearchBO;
import com.lyranxi.link.user.constant.UserConstant;
import com.lyranxi.link.user.vo.ApiVO;
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
 * @date 2025-04-16 10:23
 */
@FeignClient(name = UserConstant.SERVICE_NAME, fallback = Object.class)
public interface ApiFeign {


    /**
     * 保存接口信息
     *
     * @param params 接口信息
     * @return 操作是否成功
     */
    @PostMapping("/api/v1/inner/api/create")
    Result<Boolean> batchSave(@RequestBody @Valid List<ApiCreateBO> params);

    /**
     * 通过使用范围查询api
     *
     * @param scope 适用范围
     * @return Result<List < ApiVO>> 查询结果
     */
    @GetMapping("/api/v1/inner/api/get-by-scope")
    Result<List<ApiVO>> getByScope(@RequestParam("scope") Integer scope);

    /**
     * 查询api信息
     *
     * @param params 搜索条件
     * @return Result<List < ApiVO>> 查询结果
     */
    @PostMapping("/api/v1/inner/api/search")
    Result<List<ApiVO>> search(@RequestBody @Valid ApiSearchBO params);

    /**
     * 查询接口信息
     *
     * @param scope  适用范围
     * @param method 请求方法
     * @param uri    uri
     * @return ApiVO 接口信息
     */
    @GetMapping("/api/vi/inner/api/uri")
    Result<ApiVO> getByUri(@RequestParam("scope") Integer scope, @RequestParam("method") String method, @RequestParam("uri") String uri);

}
