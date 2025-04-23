package com.lyranxi.link.user.mapper;

import com.lyranxi.link.user.entity.MenuApiRelate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author ranxi
* @description 针对表【menu_api_relate(菜单api关联信息)】的数据库操作Mapper
* @createDate 2025-04-09 18:12:26
* @Entity com.lyranxi.link.user.entity.MenuApiRelate
*/
public interface MenuApiRelateMapper extends BaseMapper<MenuApiRelate> {

    int deleteByMenuId(@Param("menuId")Long menuId);

}




