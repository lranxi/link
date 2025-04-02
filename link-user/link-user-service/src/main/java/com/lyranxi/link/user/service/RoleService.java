package com.lyranxi.link.user.service;

import cn.hutool.core.collection.CollectionUtil;
import com.lyranxi.link.common.exception.BusinessException;
import com.lyranxi.link.common.util.asserts.AssertBusinessException;
import com.lyranxi.link.user.biz.*;
import com.lyranxi.link.user.bo.role.RoleAssignMenuBO;
import com.lyranxi.link.user.bo.role.RoleAssignUserBO;
import com.lyranxi.link.user.bo.role.RoleCreateBO;
import com.lyranxi.link.user.converter.RoleConverter;
import com.lyranxi.link.user.entity.Role;
import com.lyranxi.link.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * role service
 *
 * @author ranxi
 * @date 2025-03-24 15:41
 */
@Slf4j
@RequiredArgsConstructor
public class RoleService {

    private final RoleBiz roleBiz;
    private final RoleMenuMappingBiz roleMenuMappingBiz;
    private final UserBiz userBiz;
    private final UserRoleMappingBiz userRoleMappingBiz;

    /**
     * 创建角色
     *
     * @param params 角色信息
     * @return 是否创建成功
     */
    public boolean create(RoleCreateBO params) {
        // 检查角色名称
        this.assertRoleNameUnique(params.getTenantId(), params.getStoreId(), params.getRoleName());
        // 保存数据
        Role role = RoleConverter.INSTANCE.to(params);
        return roleBiz.insert(role);
    }

    /**
     * 更新角色名称
     *
     * @param roleId   角色ID
     * @param roleName 角色名称
     * @return 是否更新过成功
     */
    public boolean update(String roleId, String roleName) {
        Role existRole = this.assertRoleExist(roleId);
        this.assertRoleNameUniqueOnUpdate(existRole.getTenantId(), existRole.getStoreId(), roleName);

        existRole.setRoleName(roleName);
        return roleBiz.updateByPrimaryKey(existRole);
    }

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     * @return 是否删除成功
     */
    public boolean delete(String roleId) {
        this.assertRoleExist(roleId);

        return this.roleBiz.loginDelete(roleId);
    }


    /**
     * 角色分配权限
     *
     * @param params 参数信息
     * @return 操作是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean assignMenus(RoleAssignMenuBO params) {
        assertRoleExist(params.getRoleId());
        // 删除原始配置
        roleMenuMappingBiz.deleteByRoleId(params.getRoleId());
        // 保存新数据
        return roleMenuMappingBiz.insertMappings(params.getRoleId(), params.getMenuIds());
    }

    /**
     * 角色分配用户
     *
     * @param params 参数信息
     * @return 是否添加成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean assignUsers(RoleAssignUserBO params) {
        // 查询用户信息
        List<User> users = userBiz.batchGetByUid(params.getUserIds());
        AssertBusinessException.nonEmpty(users, "用户信息不存在或已删除");
        // 查询角色信息
        Role role = assertRoleExist(params.getRoleId());

        // 系统角色只能分配给系统用户
        List<User> list = users.stream().filter(item -> !Objects.equals(item.getSystem(), role.getSystem()))
                .toList();
        if (CollectionUtil.isNotEmpty(list)) {
            if (role.getSystem()) {
                throw new BusinessException("系统角色只能分配给系统用户");
            } else {
                throw new BusinessException("租户角色只能分配给租户用户");
            }
        }
        // 删除远来的绑定关系
        userRoleMappingBiz.deleteByRoleId(params.getRoleId());
        // 添加新的绑定关系
        return userRoleMappingBiz.insertMappings(params.getRoleId(), params.getUserIds());
    }


    /**
     * 断言角色名称唯一
     *
     * @param tenantId 租户ID
     * @param storeId  门店ID
     * @param roleName 角色名称
     */
    private void assertRoleNameUnique(Long tenantId, Long storeId, String roleName) {
        AssertBusinessException.isNull(this.roleBiz.getByRoleName(tenantId, storeId, roleName), "角色名称已经存在");
    }

    /**
     * 更新时断言角色名称唯一
     *
     * @param tenantId 租户ID
     * @param storeId  门店ID
     * @param roleName 角色名称
     */
    private void assertRoleNameUniqueOnUpdate(Long tenantId, Long storeId, String roleName) {
        Role role = this.roleBiz.getByRoleName(tenantId, storeId, roleName);
        AssertBusinessException.isNull(role, "角色名称已经存在");
    }

    /**
     * 断言角色存在
     *
     * @param roleId 角色ID
     */
    private Role assertRoleExist(String roleId) {
        Role role = this.roleBiz.getByRoleId(roleId);
        AssertBusinessException.nonNull(role, "角色不存在或已删除");
        return role;
    }

}
