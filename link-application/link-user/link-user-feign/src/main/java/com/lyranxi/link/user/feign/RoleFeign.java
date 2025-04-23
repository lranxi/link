package com.lyranxi.link.user.feign;

import com.lyranxi.link.common.bo.CoreBO;
import com.lyranxi.link.common.page.Pageable;
import com.lyranxi.link.user.bo.RoleCreateBO;
import com.lyranxi.link.user.bo.RoleSearchBO;
import com.lyranxi.link.user.bo.RoleUpdateBO;
import com.lyranxi.link.user.constant.UserConstant;
import com.lyranxi.link.user.vo.RoleVO;
import com.lyranxi.link.web.result.Result;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ranxi
 * @date 2025-04-18 11:27
 */
@FeignClient(name = UserConstant.SERVICE_NAME, fallback = Object.class)
public interface RoleFeign {

    /**
     * 创建角色
     *
     * @param params 角色信息
     * @return 操作解决
     */
    @PostMapping("/api/v1/inner/role/create")
    Result<Boolean> create(@RequestBody @Valid CoreBO<RoleCreateBO> params);

    /**
     * 编辑角色
     *
     * @param params 角色信息
     * @return 操作结果
     */
    @PostMapping("/api/v1/inner/role/update")
    Result<Boolean> update(@RequestBody @Valid CoreBO<RoleUpdateBO> params);


    /**
     * 删除角色
     *
     * @param roleId 角色id
     * @return 操作结果
     */
    @GetMapping("/api/v1/inner/role/delete")
    Result<Boolean> delete(@RequestParam("roleId") CoreBO<Long> params);

    /**
     * 编辑状态
     *
     * @param params 角色ID
     * @return 操作结果
     */
    @GetMapping("/api/v1/inner/role/switch-enable")
    Result<Boolean> switchEnable(@RequestParam("roleId") CoreBO<Long> params);

    /**
     * 查询角色信息
     *
     * @param params 查询条件
     * @return Pageable<RoleVO> 角色信息
     */
    @PostMapping("/api/v1/inner/role/search")
    Result<Pageable<RoleVO>> search(@RequestBody @Valid CoreBO<RoleSearchBO> params);

}
