package com.lyranxi.link.user.converter;

import com.lyranxi.link.user.bo.BrandCreateBO;
import com.lyranxi.link.user.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ranxi
 * @date 2025-04-23 14:38
 */
@Mapper
public interface BrandConverter {

    BrandConverter INSTANCE = Mappers.getMapper(BrandConverter.class);

    Brand to(BrandCreateBO params);

}
