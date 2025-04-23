package com.lyranxi.link.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyranxi.link.user.entity.User;
import org.apache.ibatis.annotations.Param;

/**
* @author ranxi
* @description 针对表【user(用户信息)】的数据库操作Mapper
* @createDate 2025-04-09 18:12:26
* @Entity com.lyranxi.link.user.entity.User
*/
public interface UserMapper extends BaseMapper<User> {

    User selectByUsername(@Param("username")String username);

    int logicDeleteByUserId(@Param("userId")Long userId,@Param("operatorId")Long operatorId);
}




