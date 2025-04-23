package com.lyranxi.link.user.converter;

import com.lyranxi.link.user.bo.MenuCreateBO;
import com.lyranxi.link.user.bo.MenuUpdateBO;
import com.lyranxi.link.user.entity.Menu;
import com.lyranxi.link.user.vo.MenuTreeSimpleVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author ranxi
 * @date 2025-04-18 16:13
 */
@Mapper
public interface MenuConverter {

    MenuConverter INSTANCE = Mappers.getMapper(MenuConverter.class);

    List<MenuTreeSimpleVO> entity2TreeSimpleVO(List<Menu> list);

    Menu to(MenuCreateBO params);

    Menu to(MenuUpdateBO params);
}
