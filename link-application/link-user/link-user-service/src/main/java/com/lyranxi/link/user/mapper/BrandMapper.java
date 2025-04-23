package com.lyranxi.link.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyranxi.link.user.entity.Brand;
import org.apache.ibatis.annotations.Param;

/**
* @author ranxi
* @description 针对表【brand(品牌信息)】的数据库操作Mapper
* @createDate 2025-04-09 18:12:26
* @Entity com.lyranxi.link.user.entity.Brand
*/
public interface BrandMapper extends BaseMapper<Brand> {

    /**
     * 查询租户已经创建的品牌数量
     * @param tenantId 租户ID
     * @return 已经创建的品牌数量
     */
    int selectTenantCreatedBrandCount(@Param("tenantId") String tenantId);

}




