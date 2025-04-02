package com.lyranxi.link.user.biz;

import com.lyranxi.link.mysql.biz.BaseBiz;
import com.lyranxi.link.user.entity.MenuApiMapping;
import com.lyranxi.link.user.mapper.MenuApiMappingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 菜单api关联关系
 * @author ranxi
 * @date 2025-04-02 19:04
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MenuApiMappingBiz extends BaseBiz<MenuApiMappingMapper, MenuApiMapping> {
}
