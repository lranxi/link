package com.lyranxi.link.user.biz;

import cn.hutool.core.collection.CollectionUtil;
import com.lyranxi.link.mysql.biz.BaseBiz;
import com.lyranxi.link.user.entity.MenuApiRelate;
import com.lyranxi.link.user.mapper.MenuApiRelateMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author ranxi
 * @date 2025-04-09 18:21
 */
@Slf4j
@Service
public class MenuApiRelateBiz extends BaseBiz<MenuApiRelateMapper, MenuApiRelate> {

    /**
     * 保存menu api 关联关系
     *
     * @param menuId     菜单ID
     * @param apiIds     api ID
     * @param operatorId 操作用户ID
     */
    public void insertRelates(Long menuId, Set<Long> apiIds, Long operatorId) {
        if (CollectionUtil.isEmpty(apiIds)) {
            return;
        }

        List<MenuApiRelate> relates = new ArrayList<>();
        for (Long apiId : apiIds) {
            MenuApiRelate relate = new MenuApiRelate();
            relate.setMenuId(menuId);
            relate.setApiId(apiId);
            relate.setCreateBy(operatorId);
        }
        this.batchInsert(relates);
    }

    /**
     * 根据菜单ID删除关联关系
     *
     * @param menuId 菜单ID
     */
    public void deleteByMenuId(Long menuId) {
        this.baseMapper.deleteByMenuId(menuId);
    }

}
