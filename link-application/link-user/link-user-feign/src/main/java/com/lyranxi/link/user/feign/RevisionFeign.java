package com.lyranxi.link.user.feign;

import com.lyranxi.link.common.bo.CoreBO;
import com.lyranxi.link.user.bo.RevisionCreateBO;
import com.lyranxi.link.user.bo.RevisionUpdateBO;
import com.lyranxi.link.user.constant.UserConstant;
import com.lyranxi.link.web.result.Result;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 版本feign
 *
 * @author ranxi
 * @date 2025-04-21 16:57
 */
@FeignClient(name = UserConstant.SERVICE_NAME, fallback = Object.class)
public interface RevisionFeign {

    /**
     * 创建版本
     *
     * @param params 版本信息
     * @return 操作结果
     */
    @PostMapping("/api/v1/inner/revision/create")
    Result<Boolean> create(@RequestBody @Valid CoreBO<RevisionCreateBO> params);


    /**
     * 编辑版本信息
     *
     * @param params 版本信息
     * @return 操作结果
     */
    @PostMapping("/api/v1/inner/revision/update")
    Result<Boolean> update(@RequestBody @Valid CoreBO<RevisionUpdateBO> params);

    /**
     * 删除版本信息
     *
     * @param params 版本ID
     * @return 是否删除成功
     */
    @PostMapping("/api/v1/inner/revision/delete")
    Result<Boolean> delete(@RequestBody @Valid CoreBO<Long> params);

    /**
     * 编辑停用启用状态
     *
     * @param params 版本ID
     * @return 操作结果
     */
    @PostMapping("/api/v1/inner/revision/switch-enable")
    Result<Boolean> switchEnable(@RequestBody @Valid CoreBO<Long> params);

}
