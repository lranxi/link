package com.lyranxi.link.user.biz;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.lyranxi.link.common.util.asserts.AssertBusinessException;
import com.lyranxi.link.common.util.uuid.UuidUtil;
import com.lyranxi.link.mysql.biz.BaseBiz;
import com.lyranxi.link.user.entity.Role;
import com.lyranxi.link.user.mapper.RoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * role biz
 *
 * @author ranxi
 * @date 2025-03-18 17:03
 */
@Slf4j
@Service
public class RoleBiz extends BaseBiz<RoleMapper, Role> {

    @Override
    public boolean insert(Role role) {
        role.setRoleId(UuidUtil.uuid22());
        return super.insert(role);
    }


    public Role getByRoleId(String roleId) {
        return this.baseMapper.selectOne(Wrappers.<Role>lambdaQuery()
                .eq(Role::getRoleId, roleId)
                .eq(Role::getDeleted, Boolean.FALSE));
    }

    public boolean loginDelete(String roleId) {
        return SqlHelper.retBool(this.baseMapper.logicDelete(roleId));
    }


    public Role getByRoleName(Long tenantId, Long storeId, String roleName) {
        return this.baseMapper.selectOne(Wrappers.<Role>lambdaQuery()
                .eq(Role::getTenantId, tenantId)
                .eq(Role::getStoreId, storeId)
                .eq(Role::getRoleName, roleName)
                .eq(Role::getDeleted, Boolean.FALSE));
    }

    public Role assertRoleExist(String roleId) {
        Role role = this.getByRoleId(roleId);
        AssertBusinessException.nonNull(role, "角色不存在或已经删除");
        return role;
    }

}
