package com.lyranxi.link.user.converter;

import com.lyranxi.link.user.bo.RoleCreateBO;
import com.lyranxi.link.user.bo.RoleUpdateBO;
import com.lyranxi.link.user.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ranxi
 * @date 2025-04-21 11:35
 */
@Mapper
public interface RoleConverter {

    RoleConverter INSTANCE = Mappers.getMapper(RoleConverter.class);

    Role to(RoleCreateBO params);

    Role to(RoleUpdateBO params);

}
