package com.lyranxi.link.user.converter;

import com.lyranxi.link.user.bo.user.UserRegisterBO;
import com.lyranxi.link.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ranxi
 * @date 2025-03-14 14:41
 */
@Mapper
public interface SignupConverter {

    SignupConverter Mapper = Mappers.getMapper(SignupConverter.class);

    User toEntity(UserRegisterBO params);

}
