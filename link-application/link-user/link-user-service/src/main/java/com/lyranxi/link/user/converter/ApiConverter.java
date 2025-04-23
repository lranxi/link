package com.lyranxi.link.user.converter;

import com.lyranxi.link.user.bo.ApiCreateBO;
import com.lyranxi.link.user.entity.Api;
import com.lyranxi.link.user.vo.ApiVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * api 数据转化器
 *
 * @author ranxi
 * @date 2025-04-10 18:07
 */
@Mapper
public interface ApiConverter {

    ApiConverter INSTANCE = Mappers.getMapper(ApiConverter.class);

    List<Api> bo2Entity(List<ApiCreateBO> params);

    ApiVO to(Api api);

    Api to(ApiCreateBO param);

    List<ApiVO> entity2Vo(List<Api> apis);
}
