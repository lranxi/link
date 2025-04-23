package com.lyranxi.link.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyranxi.link.user.entity.Tenant;
import com.lyranxi.link.user.token.UserTenantInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author ranxi
 * @description 针对表【tenant(租户信息)】的数据库操作Mapper
 * @createDate 2025-04-09 18:12:26
 * @Entity com.lyranxi.link.user.entity.Tenant
 */
public interface TenantMapper extends BaseMapper<Tenant> {


    /**
     * 查询租户下所有品牌和门店
     * @param tenantId 租户ID
     * @return List<UserTenantInfo>
     */
    Set<UserTenantInfo> getTenantAllStores(@Param("tenantId") String tenantId);

    /**
     * 查询品牌下所有门店
     * @param brandId 品牌ID
     * @return List<UserTenantInfo> 品牌下的所有门店
     */
    Set<UserTenantInfo> getBrandAllStores(@Param("brandId") String brandId);

    /**
     * 查询门店信息
     * @param storeId 门店ID
     * @return UserTenantInfo 门店信息
     */
    UserTenantInfo getStore(@Param("storeId")String storeId);

}




