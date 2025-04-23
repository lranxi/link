package com.lyranxi.link.user.service;

import com.lyranxi.link.common.bo.CoreBO;
import com.lyranxi.link.user.biz.MenuApiRelateBiz;
import com.lyranxi.link.user.biz.MenuBiz;
import com.lyranxi.link.user.bo.MenuCreateBO;
import com.lyranxi.link.user.bo.MenuUpdateBO;
import com.lyranxi.link.user.converter.MenuConverter;
import com.lyranxi.link.user.entity.Menu;
import com.lyranxi.link.user.feign.MenuFeign;
import com.lyranxi.link.web.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ranxi
 * @date 2025-04-21 18:42
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class MenuService implements MenuFeign {

    private final MenuBiz menuBiz;
    private final MenuApiRelateBiz menuApiRelateBiz;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<Boolean> create(CoreBO<MenuCreateBO> params) {
        MenuCreateBO content = params.getContent();
        menuBiz.assertMenuNameNotExistOnCreate(content.getName(), content.getPlatform());

        Menu menu = MenuConverter.INSTANCE.to(content);
        menu.setCreateBy(params.getOperatorId());
        menuBiz.insert(menu);

        // 绑定api
        menuApiRelateBiz.insertRelates(menu.getId(), content.getApiIds(), params.getOperatorId());
        return Result.success(Boolean.TRUE);
    }

    @Override
    public Result<Boolean> update(CoreBO<MenuUpdateBO> params) {
        MenuUpdateBO content = params.getContent();
        menuBiz.assertMenuNameNotExistOnUpdate(content.getId(), content.getName(), content.getPlatform());

        menuBiz.getByIdMust(content.getId());

        Menu menu = MenuConverter.INSTANCE.to(content);
        menu.setCreateBy(params.getOperatorId());
        menuBiz.updateByPrimaryKey(menu);

        // 菜单绑定api
        menuApiRelateBiz.deleteByMenuId(content.getId());
        menuApiRelateBiz.insertRelates(content.getId(), content.getApiIds(), params.getOperatorId());
        return Result.success(Boolean.TRUE);
    }
}
