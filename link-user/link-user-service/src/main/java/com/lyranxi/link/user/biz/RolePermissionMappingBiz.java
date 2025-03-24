package com.lyranxi.link.user.biz;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.lyranxi.link.mysql.biz.BaseBiz;
import com.lyranxi.link.user.entity.RolePermissionMapping;
import com.lyranxi.link.user.mapper.RolePermissionMappingMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author ranxi
 * @date 2025-03-18 18:10
 */
@Slf4j
@Service
public class RolePermissionMappingBiz extends BaseBiz<RolePermissionMappingMapper, RolePermissionMapping> {

    public List<RolePermissionMapping> getByRoleId(String roleId) {
        return this.baseMapper.selectList(Wrappers.<RolePermissionMapping>lambdaQuery()
                .eq(RolePermissionMapping::getRoleId, roleId));
    }

    public boolean deleteByRoleId(String roleId) {
        return SqlHelper.retBool(this.baseMapper.delete(Wrappers.<RolePermissionMapping>lambdaQuery()
                .eq(RolePermissionMapping::getRoleId, roleId)));
    }

    public boolean insertMappings(String roleId, Set<String> permissionIds) {
        List<RolePermissionMapping> list = permissionIds.stream().map(
                permissionId -> RolePermissionMapping.builder()
                        .roleId(roleId)
                        .permissionId(permissionId)
                        .build()).toList();
        return this.batchInsert(list);
    }

}
