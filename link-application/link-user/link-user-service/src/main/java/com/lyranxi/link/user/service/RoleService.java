package com.lyranxi.link.user.service;

import com.lyranxi.link.common.bo.CoreBO;
import com.lyranxi.link.common.page.Pageable;
import com.lyranxi.link.common.util.asserts.AssertBusinessException;
import com.lyranxi.link.user.biz.RoleBiz;
import com.lyranxi.link.user.biz.RoleMenuRelateBiz;
import com.lyranxi.link.user.biz.UserRoleRelateBiz;
import com.lyranxi.link.user.bo.RoleCreateBO;
import com.lyranxi.link.user.bo.RoleSearchBO;
import com.lyranxi.link.user.bo.RoleUpdateBO;
import com.lyranxi.link.user.entity.Role;
import com.lyranxi.link.user.feign.RoleFeign;
import com.lyranxi.link.user.vo.RoleVO;
import com.lyranxi.link.web.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author ranxi
 * @date 2025-04-18 11:27
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class RoleService implements RoleFeign {

    private final RoleBiz roleBiz;
    private final RoleMenuRelateBiz roleMenuRelateBiz;
    private final UserRoleRelateBiz userRoleRelateBiz;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<Boolean> create(CoreBO<RoleCreateBO> params) {
        // 添加角色
        Role role = roleBiz.save(params.getContent(), params.getOperatorId());
        // 添加菜单绑定关系
        Set<Long> menuIds = params.getContent().getMenuIds();
        return Result.success(roleMenuRelateBiz.insertRelates(role.getId(), menuIds, params.getOperatorId()));
    }

    @Transactional
    @Override
    public Result<Boolean> update(CoreBO<RoleUpdateBO> params) {
        roleBiz.update(params.getContent());
        // 更新菜单绑定关系
        roleMenuRelateBiz.deleteByRoleId(params.getContent().getId());
        roleMenuRelateBiz.insertRelates(params.getContent().id, params.getContent().getMenuIds(), params.getOperatorId());
        return Result.success(Boolean.TRUE);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<Boolean> delete(CoreBO<Long> params) {
        AssertBusinessException.isEmpty(
                userRoleRelateBiz.getUserByRoleId(params.getContent()),
                "当前角色已经授权给用户,请解除授权后再进行删除"
        );

        roleBiz.loginDelete(params.getContent(), params.getOperatorId());
        roleMenuRelateBiz.deleteByRoleId(params.getContent());
        return Result.success(Boolean.TRUE);
    }

    @Override
    public Result<Boolean> switchEnable(CoreBO<Long> params) {
        Role role = roleBiz.assertRoleExist(params.getContent());
        role.setEnable(!role.getEnable());
        role.setUpdateBy(params.getOperatorId());
        return Result.success(roleBiz.updateByPrimaryKey(role));
    }

    @Override
    public Result<Pageable<RoleVO>> search(CoreBO<RoleSearchBO> params) {
        return Result.success(roleBiz.search(params.getPageInfo(), params.getContent()));
    }
}
