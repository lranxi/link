package com.lyranxi.link.user.mapper;

import com.lyranxi.link.user.entity.UserRoleMapping;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author ranxi
* @description 针对表【user_role_mapping(用户角色映射表)】的数据库操作Mapper
* @createDate 2025-03-18 18:07:46
* @Entity com.lyranxi.link.user.entity.UserRoleMapping
*/
public interface UserRoleMappingMapper extends BaseMapper<UserRoleMapping> {

    int deleteByRoleId(@Param("roleId") String roleId);

}




