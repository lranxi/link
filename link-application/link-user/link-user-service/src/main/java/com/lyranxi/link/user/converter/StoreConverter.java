package com.lyranxi.link.user.converter;

import com.lyranxi.link.user.bo.StoreCreateBO;
import com.lyranxi.link.user.entity.Store;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ranxi
 * @date 2025-04-23 15:08
 */
@Mapper
public interface StoreConverter {

    StoreConverter INSTANCE = Mappers.getMapper(StoreConverter.class);

    Store to(StoreCreateBO params);

}
