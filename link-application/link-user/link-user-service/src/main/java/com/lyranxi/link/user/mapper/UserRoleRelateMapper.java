package com.lyranxi.link.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyranxi.link.user.entity.Role;
import com.lyranxi.link.user.entity.User;
import com.lyranxi.link.user.entity.UserRoleRelate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author ranxi
* @description 针对表【user_role_relate(用户角色映射表)】的数据库操作Mapper
* @createDate 2025-04-09 18:12:26
* @Entity com.lyranxi.link.user.entity.UserRoleRelate
*/
public interface UserRoleRelateMapper extends BaseMapper<UserRoleRelate> {

    /**
     * 根据用户ID查询用户的角色信息
     * @param userId 用户ID
     * @return List<Role> 角色信息
     */
    List<Role> selectRolesByUserId(@Param("userId") Long userId);

    /**
     * 根据角色ID查询用户信息
     * @param roleId 角色ID
     * @return List<User> 用户信息
     */
    List<User> getUserByRoleId(@Param("roleId") Long roleId);
}




