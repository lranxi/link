package com.lyranxi.link.user.feign;

import com.lyranxi.link.common.bo.CoreBO;
import com.lyranxi.link.user.bo.MenuCreateBO;
import com.lyranxi.link.user.bo.MenuUpdateBO;
import com.lyranxi.link.user.constant.UserConstant;
import com.lyranxi.link.web.result.Result;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author ranxi
 * @date 2025-04-21 18:27
 */
@FeignClient(name = UserConstant.SERVICE_NAME, fallback = Object.class)
public interface MenuFeign {

    /**
     * 创建菜单
     *
     * @param params 菜单信息
     * @return 是否创建成功
     */
    @PostMapping("/api/v1/inner/menu/create")
    Result<Boolean> create(@RequestBody @Valid CoreBO<MenuCreateBO> params);

    /**
     * 编辑菜单
     *
     * @param params 菜单信息
     * @return 是否编辑成功
     */
    @PostMapping("/api/v1/inner/menu/update")
    Result<Boolean> update(@RequestBody @Valid CoreBO<MenuUpdateBO> params);

}
