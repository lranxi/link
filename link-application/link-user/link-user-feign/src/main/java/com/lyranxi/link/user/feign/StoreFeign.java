package com.lyranxi.link.user.feign;

import com.lyranxi.link.common.bo.CoreBO;
import com.lyranxi.link.user.bo.StoreCreateBO;
import com.lyranxi.link.user.constant.UserConstant;
import com.lyranxi.link.web.result.Result;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author ranxi
 * @date 2025-04-23 14:35
 */
@FeignClient(name = UserConstant.SERVICE_NAME, fallback = Object.class)
public interface StoreFeign {

    /**
     * 创建品牌
     *
     * @param params 品牌信息
     * @return 是否创建成功
     */
    @PostMapping("/api/v1/inner/store/create")
    Result<Boolean> create(@RequestBody @Valid CoreBO<StoreCreateBO> params);

}
