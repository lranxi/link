package com.lyranxi.link.user.mapper;

import com.lyranxi.link.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author ranxi
* @description 针对表【user(用户信息)】的数据库操作Mapper
* @date 2025-03-13 18:12:39
* @Entity  com.lyranxi.link.user.entity.User
*/
@Repository
public interface UserMapper extends BaseMapper<User> {

}




