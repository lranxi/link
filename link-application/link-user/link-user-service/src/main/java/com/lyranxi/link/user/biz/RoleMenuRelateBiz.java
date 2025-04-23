package com.lyranxi.link.user.biz;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.lyranxi.link.mysql.biz.BaseBiz;
import com.lyranxi.link.user.entity.RoleMenuRelate;
import com.lyranxi.link.user.mapper.RoleMenuRelateMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author ranxi
 * @date 2025-04-09 18:23
 */
@Slf4j
@Service
public class RoleMenuRelateBiz extends BaseBiz<RoleMenuRelateMapper, RoleMenuRelate> {


    /**
     * 根据角色ID获取关联关系
     *
     * @param roleId 角色ID
     * @return boolean 是否操作成功
     */
    public List<RoleMenuRelate> getByRoleId(Long roleId) {
        return this.baseMapper.selectList(Wrappers.<RoleMenuRelate>lambdaQuery()
                .eq(RoleMenuRelate::getRoleId, roleId));
    }

    /**
     * 根据角色ID删除关联关系
     *
     * @param roleId 角色ID
     * @return boolean 是否操作成功
     */
    public boolean deleteByRoleId(Long roleId) {
        return SqlHelper.retBool(this.baseMapper.delete(Wrappers.<RoleMenuRelate>lambdaQuery()
                .eq(RoleMenuRelate::getRoleId, roleId)));
    }

    /**
     * 批量保存角色菜单关联关系
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID集合
     * @return boolean 是否保存成功
     */
    public boolean insertRelates(Long roleId, Set<Long> menuIds, Long operatorId) {
        List<RoleMenuRelate> mappings = menuIds.stream().map(
                menuId -> RoleMenuRelate.builder()
                        .roleId(roleId)
                        .menuId(menuId)
                        .createBy(operatorId)
                        .build()
        ).toList();
        return this.batchInsert(mappings);
    }



}
