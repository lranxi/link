package com.lyranxi.link.user.biz;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lyranxi.link.common.util.asserts.AssertRequestParameter;
import com.lyranxi.link.mysql.biz.BaseBiz;
import com.lyranxi.link.user.entity.Menu;
import com.lyranxi.link.user.mapper.MenuMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ranxi
 * @date 2025-04-09 18:20
 */
@Slf4j
@Service
public class MenuBiz extends BaseBiz<MenuMapper, Menu> {

    /**
     * 查询角色制定平台的菜单列表
     *
     * @param roleId   角色ID
     * @param platform 平台ID
     * @return List<Menu> 菜单信息
     */
    public List<Menu> getMenuListByRoleIdAndPlatform(Long roleId, Integer platform) {
        return this.baseMapper.selectMenuListByRoleIdAndPlatform(roleId, platform);
    }

    /**
     * 根据ID查询菜单
     *
     * @param menuId 菜单ID
     * @return Menu
     * @throws com.lyranxi.link.common.exception.BusinessException 菜单不存在
     */
    public Menu getByIdMust(Long menuId) {
        Menu menu = this.getByPrimaryKey(menuId);
        AssertRequestParameter.nonNull(menu, "菜单不存在");
        return menu;
    }

    public List<Menu> getByNameAndPlatform(String name, Integer platform) {
        return this.baseMapper.selectList(Wrappers.lambdaQuery(Menu.class)
                .eq(Menu::getName, name)
                .eq(Menu::getPlatform, platform));
    }

    public void assertMenuNameNotExistOnCreate(String name, Integer platform) {
        List<Menu> menus = this.getByNameAndPlatform(name, platform);
        AssertRequestParameter.isTrue(menus.isEmpty(), "菜单名称已存在");
    }

    public void assertMenuNameNotExistOnUpdate(Long menuId, String name, Integer platform) {
        List<Menu> menus = this.getByNameAndPlatform(name, platform);
        AssertRequestParameter.isTrue(
                menus.isEmpty() || menus.size() == 1 && menus.getFirst().getId().equals(menuId),
                "菜单名称已存在");
    }

}
