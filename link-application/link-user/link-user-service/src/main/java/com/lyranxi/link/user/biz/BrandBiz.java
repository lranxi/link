package com.lyranxi.link.user.biz;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lyranxi.link.mysql.biz.BaseBiz;
import com.lyranxi.link.user.bo.BrandCreateBO;
import com.lyranxi.link.user.converter.BrandConverter;
import com.lyranxi.link.user.entity.Brand;
import com.lyranxi.link.user.mapper.BrandMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * brand biz
 *
 * @author ranxi
 * @date 2025-04-09 18:20
 */
@Slf4j
@Service
public class BrandBiz extends BaseBiz<BrandMapper, Brand> {

    /**
     * 查询租户已经创建的品牌数量
     *
     * @param tenantId 租户ID
     * @return int 已经创建的品牌数量
     */
    public int getTenantCreatedBrandCount(String tenantId) {
        return this.baseMapper.selectTenantCreatedBrandCount(tenantId);
    }


    /**
     * 创建品牌
     *
     * @param params     品牌信息
     * @param operatorId 操作用户ID
     * @return boolean 是否创建成功
     */
    public boolean create(BrandCreateBO params, Long operatorId) {
        Brand brand = BrandConverter.INSTANCE.to(params);
        brand.setCreateBy(operatorId);
        return this.insert(brand);
    }

    /**
     * 根据租户ID和品牌名称查询租户信息
     *
     * @param tenantId 租户ID
     * @param name     名称
     * @return brand 品牌信息
     */
    public Brand getByTenantIdAndName(String tenantId, String name) {
        return this.baseMapper.selectOne(Wrappers.lambdaQuery(Brand.class)
                .eq(Brand::getTenantId, tenantId)
                .eq(Brand::getName, name)
                .eq(Brand::getDeleted, Boolean.FALSE));
    }

}
