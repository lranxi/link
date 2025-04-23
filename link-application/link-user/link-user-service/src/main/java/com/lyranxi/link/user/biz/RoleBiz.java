package com.lyranxi.link.user.biz;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.lyranxi.link.common.constant.GlobalConstant;
import com.lyranxi.link.common.page.PageInfo;
import com.lyranxi.link.common.page.Pageable;
import com.lyranxi.link.common.util.asserts.AssertBusinessException;
import com.lyranxi.link.mysql.biz.BaseBiz;
import com.lyranxi.link.mysql.page.Page;
import com.lyranxi.link.user.bo.RoleCreateBO;
import com.lyranxi.link.user.bo.RoleSearchBO;
import com.lyranxi.link.user.bo.RoleUpdateBO;
import com.lyranxi.link.user.converter.RoleConverter;
import com.lyranxi.link.user.entity.Role;
import com.lyranxi.link.user.enums.RoleScopeEnum;
import com.lyranxi.link.user.mapper.RoleMapper;
import com.lyranxi.link.user.vo.RoleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author ranxi
 * @date 2025-04-09 18:22
 */
@Slf4j
@Service
public class RoleBiz extends BaseBiz<RoleMapper, Role> {

    /**
     * 根据ID查询
     *
     * @param id 角色ID
     * @return Role 角色信息
     */
    public Role getById(Long id) {
        return this.baseMapper.selectOne(Wrappers.<Role>lambdaQuery()
                .eq(Role::getId, id)
                .eq(Role::getDeleted, Boolean.FALSE));
    }

    /**
     * 逻辑删除角色
     *
     * @param roleId 角色ID
     * @return boolean 是否删除成功
     */
    public boolean loginDelete(Long roleId, Long operatorId) {
        return SqlHelper.retBool(this.baseMapper.logicDelete(roleId, operatorId));
    }


    /**
     * 根据角色名称查询角色
     *
     * @param tenantId 租户ID
     * @param brandId  品牌ID
     * @param storeId  门店ID
     * @param roleName 角色名称
     * @return Role 角色信息
     */
    public Role getByRoleName(Long tenantId, Long brandId, Long storeId, String roleName) {
        return this.baseMapper.selectOne(Wrappers.<Role>lambdaQuery()
                .eq(Role::getTenantId, tenantId)
                .eq(Role::getBrandId, brandId)
                .eq(Role::getStoreId, storeId)
                .eq(Role::getRoleName, roleName)
                .eq(Role::getDeleted, Boolean.FALSE));
    }

    /**
     * 断言角色存在
     *
     * @param roleId 角色ID
     * @return Role 角色信息
     * @throws com.lyranxi.link.common.exception.BusinessException 角色不存在时跑出异常
     */
    public Role assertRoleExist(Long roleId) {
        Role role = this.getById(roleId);
        AssertBusinessException.nonNull(role, "角色不存在或已经删除");
        return role;
    }

    /**
     * 查询用户的角色信息
     *
     * @param userId 用户ID
     * @return Role 用户角色信息
     */
    public Role getRoleByUserId(Long userId) {
        return this.baseMapper.selectUserRole(userId);
    }


    /**
     * 查询用户拥有的api信息
     *
     * @param userId   userId
     * @param platform 平台
     * @return Set<Long> apiIds
     */
    public Set<Long> getApiIdsByUserIdAndPlatform(Long userId, Integer platform) {
        Role role = this.getRoleByUserId(userId);
        AssertBusinessException.nonNull(role, "用户未分配角色");
        // 查询菜单信息
        return this.baseMapper.selectApiIdsByRoleIdAndPlatform(role.getId(), platform);
    }

    /**
     * 保存角色信息
     *
     * @param params 角色信息
     * @return 是否保存成功
     */
    public Role save(RoleCreateBO params, Long operatorId) {
        Role role = RoleConverter.INSTANCE.to(params);
        role.setCreateBy(operatorId);
        role.setSystem(StrUtil.equals(GlobalConstant.LINK_TENANT_ID, params.getTenantId()));
        // scope
        role.setRoleScope(getRoleScope(role).getId());
        this.insert(role);
        return role;
    }

    /**
     * 更新角色
     *
     * @param params 角色信息
     * @return boolean 是否编辑成功
     */
    public Boolean update(RoleUpdateBO params) {
        Role role = assertRoleExist(params.getId());
        return this.updateByPrimaryKey(role);
    }


    /**
     * 计算角色权限范围
     *
     * @param role 角色信息
     * @return RoleScopeEnum 权限范围
     */
    public RoleScopeEnum getRoleScope(Role role) {
        if (StrUtil.isNotBlank(role.getStoreId())) {
            return RoleScopeEnum.STORE;
        }
        if (StrUtil.isBlank(role.getStoreId()) && StrUtil.isNotBlank(role.getBrandId())) {
            return RoleScopeEnum.BRAND;
        }
        return RoleScopeEnum.TENANT;
    }


    public Pageable<RoleVO> search(PageInfo pageInfo, RoleSearchBO params) {
        return this.baseMapper.selectByCondition(Page.from(pageInfo), params);
    }


    /**
     * 创建版本模板角色
     *
     * @param revisionName 版本名称
     * @param operatorId   操作用户ID
     * @return Role 模板角色
     */
    public Role createRevisionTemplateRole(String revisionName, Long operatorId) {
        Role role = new Role();
        role.setTenantId(GlobalConstant.LINK_TENANT_ID);
        role.setRoleName(revisionName + "模板角色");
        role.setRoleScope(RoleScopeEnum.TENANT.getId());
        role.setSystem(Boolean.TRUE);
        role.setCreateBy(operatorId);
        this.insert(role);
        return role;
    }

}
