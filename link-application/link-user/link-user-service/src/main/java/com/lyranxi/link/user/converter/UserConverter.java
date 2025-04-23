package com.lyranxi.link.user.converter;

import com.lyranxi.link.user.bo.UserCreateBO;
import com.lyranxi.link.user.entity.User;
import com.lyranxi.link.user.vo.UserSimpleInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ranxi
 * @date 2025-04-15 11:54
 */
@Mapper
public interface UserConverter {

    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    User to(UserCreateBO bo);

    UserSimpleInfoVO entity2SimpleInfo(User user);
}
