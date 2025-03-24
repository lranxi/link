package com.lyranxi.link.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyranxi.link.user.entity.Role;
import org.apache.ibatis.annotations.Param;

/**
 * @author ranxi
 * @description 针对表【role(角色信息表)】的数据库操作Mapper
 * @createDate 2025-03-18 17:00:08
 * @Entity com.lyranxi.link.user.entity.Role
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 逻辑删除
     *
     * @param roleId 角色ID
     * @return 影响的行数
     */
    int logicDelete(@Param("roleId") String roleId);

}




