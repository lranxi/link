package com.lyranxi.link.user.converter;

import com.lyranxi.link.user.bo.permission.PermissionCreateBO;
import com.lyranxi.link.user.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author ranxi
 * @date 2025-03-24 18:29
 */
@Mapper
public interface PermissionConverter {

    PermissionConverter INSTANCE = Mappers.getMapper(PermissionConverter.class);

    List<Permission> to(List<PermissionCreateBO> params);

}
