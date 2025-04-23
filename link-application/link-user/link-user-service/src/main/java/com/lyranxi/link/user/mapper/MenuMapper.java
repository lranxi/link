package com.lyranxi.link.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyranxi.link.user.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ranxi
 * @description 针对表【menu(菜单信息)】的数据库操作Mapper
 * @createDate 2025-04-09 18:12:26
 * @Entity com.lyranxi.link.user.entity.Menu
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 查询角色制定平台的菜单列表
     *
     * @param roleId   角色ID
     * @param platform 平台ID
     * @return List<Menu> 菜单信息
     */
    List<Menu> selectMenuListByRoleIdAndPlatform(@Param("roleId") Long roleId, @Param("platform") Integer platform);

}




