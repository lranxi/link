package com.lyranxi.link.user.biz;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.lyranxi.link.common.util.asserts.AssertBusinessException;
import com.lyranxi.link.mysql.biz.BaseBiz;
import com.lyranxi.link.user.entity.Role;
import com.lyranxi.link.user.entity.User;
import com.lyranxi.link.user.entity.UserRoleRelate;
import com.lyranxi.link.user.mapper.UserRoleRelateMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * UserRoleRelateBiz
 *
 * @author ranxi
 * @date 2025-04-09 18:24
 */
@Slf4j
@Service
public class UserRoleRelateBiz extends BaseBiz<UserRoleRelateMapper, UserRoleRelate> {

    /**
     * 查询用户权限范围最大的角色
     *
     * @param userId 用户ID
     * @return Role 权限范围最大的角色信息
     */
    public Role getUserMaxScopeRole(Long userId) {
        List<Role> roles = this.baseMapper.selectRolesByUserId(userId);
        AssertBusinessException.nonEmpty(roles, "用未配置角色");
        // 存在多个角色使用RoleScope最小的进行处理
        return roles.stream().min(Comparator.comparingInt(Role::getRoleScope)).get();
    }

    /**
     * 批量保存用户关联关系
     *
     * @param roleId  角色ID
     * @param userIds 用户ID列表
     * @return boolean 是否保存成功
     */
    public boolean insertRelates(Long roleId, Set<Long> userIds, Long operatorId) {
        List<UserRoleRelate> relates = new ArrayList<>(userIds.size());
        for (Long userId : userIds) {
            relates.add(UserRoleRelate.builder()
                    .roleId(roleId)
                    .userId(userId)
                    .createBy(operatorId)
                    .build());
        }
        return this.batchInsert(relates, 200);
    }

    /**
     * 根据角色ID删除关联关系
     *
     * @param roleId 角色ID
     * @return boolean 是否操作成功
     */
    public boolean deleteByRoleId(Long roleId) {
        return SqlHelper.retBool(
                this.baseMapper.delete(Wrappers.<UserRoleRelate>lambdaQuery().eq(UserRoleRelate::getRoleId, roleId))
        );
    }

    /**
     * 根据用户ID删除关联关系
     *
     * @param userId 用户ID
     * @return boolean 是否操作成功
     */
    public boolean deleteByUserId(Long userId) {
        return SqlHelper.retBool(
                this.baseMapper.delete(Wrappers.<UserRoleRelate>lambdaQuery().eq(UserRoleRelate::getUserId, userId))
        );
    }

    /**
     * 查询用户的角色信息
     *
     * @param userId 用户ID
     * @return List<Role> 角色信息
     */
    public List<Role> getRolesByUserId(Long userId) {
        return this.baseMapper.selectRolesByUserId(userId);
    }

    /**
     * 根据角色ID查询绑定用户信息
     *
     * @param roleId roleId
     * @return List<User> 用户信息
     */
    public List<User> getUserByRoleId(Long roleId) {
        return this.baseMapper.getUserByRoleId(roleId);
    }

}
