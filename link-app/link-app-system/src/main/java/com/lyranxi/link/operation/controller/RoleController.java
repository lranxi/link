package com.lyranxi.link.user.controller;

import com.lyranxi.link.common.annotation.Api;
import com.lyranxi.link.common.page.Pageable;
import com.lyranxi.link.user.bo.RoleCreateBO;
import com.lyranxi.link.user.bo.RoleSearchBO;
import com.lyranxi.link.user.bo.RoleUpdateBO;
import com.lyranxi.link.user.feign.RoleFeign;
import com.lyranxi.link.user.http.HttpContext;
import com.lyranxi.link.user.vo.RoleVO;
import com.lyranxi.link.web.result.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 角色接口
 *
 * @author ranxi
 * @date 2025-04-23 16:50
 */
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleFeign roleFeign;

    /**
     * 创建角色
     *
     * @param params 角色信息
     * @return boolean 是否创建成功
     */
    @Api(name = "创建角色", author = "lyranxi")
    @PostMapping("/create")
    public Result<Boolean> create(@RequestBody @Valid RoleCreateBO params) {
        return roleFeign.create(HttpContext.build(params));
    }

    /**
     * 编辑角色
     *
     * @param params 角色信息
     * @return boolean 是否编辑成功
     */
    @Api(name = "编辑角色", author = "lyranxi")
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody @Valid RoleUpdateBO params) {
        return roleFeign.update(HttpContext.build(params));
    }

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     * @return boolean 是否删除成功
     */
    @Api(name = "删除角色", author = "lyranxi")
    @PostMapping("/delete")
    public Result<Boolean> delete(@RequestParam("roleId") Long roleId) {
        return roleFeign.delete(HttpContext.build(roleId));
    }


    /**
     * 修改角色状态
     *
     * @param roleId 角色ID
     * @return boolean 是否修改成功
     */
    @Api(name = "修改角色状态", author = "lyranxi")
    @PostMapping("/switch-enable")
    public Result<Boolean> switchEnable(@RequestParam("roleId") Long roleId) {
        return roleFeign.switchEnable(HttpContext.build(roleId));
    }

    /**
     * 查询角色信息
     *
     * @param params 查询条件
     * @return RoleVO 角色信息
     */
    @Api(name = "查询角色信息", author = "lyranxi")
    @PostMapping("/search")
    public Result<Pageable<RoleVO>> search(@RequestBody @Valid RoleSearchBO params) {
        return roleFeign.search(HttpContext.build(params));
    }

}
