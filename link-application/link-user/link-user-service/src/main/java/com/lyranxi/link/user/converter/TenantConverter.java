package com.lyranxi.link.user.converter;

import com.lyranxi.link.user.bo.TenantCreateBO;
import com.lyranxi.link.user.entity.Tenant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ranxi
 * @date 2025-04-23 11:57
 */
@Mapper
public interface TenantConverter {

    TenantConverter INSTANCE = Mappers.getMapper(TenantConverter.class);

    Tenant to(TenantCreateBO params);

}
