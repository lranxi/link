package com.lyranxi.link.user.converter;

import com.lyranxi.link.user.bo.role.RoleCreateBO;
import com.lyranxi.link.user.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ranxi
 * @date 2025-03-24 15:50
 */

@Mapper
public interface RoleConverter {

    RoleConverter INSTANCE = Mappers.getMapper(RoleConverter.class);

    Role to(RoleCreateBO params);

}
