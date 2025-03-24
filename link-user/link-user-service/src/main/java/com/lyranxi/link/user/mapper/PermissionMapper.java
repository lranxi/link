package com.lyranxi.link.user.mapper;

import com.lyranxi.link.user.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
* @author ranxi
* @description 针对表【permission(权限表)】的数据库操作Mapper
* @createDate 2025-03-18 17:00:08
* @Entity com.lyranxi.link.user.entity.Permission
*/
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> selectAll();

    int batchUpdateById(@Param("permission") List<Permission> permissions);

}




