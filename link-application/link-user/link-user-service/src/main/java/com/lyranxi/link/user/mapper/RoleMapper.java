package com.lyranxi.link.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lyranxi.link.common.page.Pageable;
import com.lyranxi.link.mysql.page.Page;
import com.lyranxi.link.user.bo.RoleSearchBO;
import com.lyranxi.link.user.entity.Role;
import com.lyranxi.link.user.vo.RoleVO;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @author ranxi
 * @description 针对表【role(角色信息表)】的数据库操作Mapper
 * @createDate 2025-04-09 18:12:26
 * @Entity com.lyranxi.link.user.entity.Role
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 逻辑删除
     *
     * @param roleId 角色ID
     * @return 影响的行数
     */
    int logicDelete(@Param("roleId") Long roleId,@Param("operatorId")Long operatorId);

    /**
     * 查询用户的角色
     *
     * @param userId 用户ID
     * @return Role 角色信息
     */
    Role selectUserRole(@Param("userId") Long userId);

    /**
     * 根据角色查询用户拥有的api权限
     *
     * @param roleId   角色ID
     * @param platform 平台
     * @return Set<Long> 接口id集合
     */
    Set<Long> selectApiIdsByRoleIdAndPlatform(@Param("roleId") Long roleId, @Param("platform") Integer platform);

    /**
     * 分页查询角色信息
     *
     * @param pageInfo 分页信息
     * @param params   查询条件
     * @return Page<RoleVO> 分页结果
     */
    Page<RoleVO> selectByCondition(Page<RoleVO> pageInfo, @Param("params") RoleSearchBO params);
}




