package com.lyranxi.link.user.biz;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.lyranxi.link.mysql.biz.BaseBiz;
import com.lyranxi.link.user.entity.RoleMenuMapping;
import com.lyranxi.link.user.mapper.RoleMenuMappingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 角色菜单关联关系biz
 *
 * @author ranxi
 * @date 2025-04-02 18:51
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleMenuMappingBiz extends BaseBiz<RoleMenuMappingMapper, RoleMenuMapping> {

    public List<RoleMenuMapping> getByRoleId(String roleId) {
        return this.baseMapper.selectList(Wrappers.<RoleMenuMapping>lambdaQuery()
                .eq(RoleMenuMapping::getRoleId, roleId));
    }

    public boolean deleteByRoleId(String roleId) {
        return SqlHelper.retBool(this.baseMapper.delete(Wrappers.<RoleMenuMapping>lambdaQuery()
                .eq(RoleMenuMapping::getRoleId, roleId)));
    }

    public boolean insertMappings(String roleId, Set<String> menuIds) {
        List<RoleMenuMapping> mappings = menuIds.stream().map(
                menuId -> RoleMenuMapping.builder()
                        .roleId(roleId)
                        .menuId(menuId)
                        .build()
        ).toList();
        return this.batchInsert(mappings);
    }

}
