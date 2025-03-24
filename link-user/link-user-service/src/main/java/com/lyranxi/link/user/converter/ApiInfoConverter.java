package com.lyranxi.link.user.converter;

import com.lyranxi.link.user.bo.api.ApiInfoCreateBO;
import com.lyranxi.link.user.entity.ApiInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author ranxi
 * @date 2025-03-24 18:29
 */
@Mapper
public interface ApiInfoConverter {

    ApiInfoConverter INSTANCE = Mappers.getMapper(ApiInfoConverter.class);

    List<ApiInfo> to(List<ApiInfoCreateBO> params);

}
