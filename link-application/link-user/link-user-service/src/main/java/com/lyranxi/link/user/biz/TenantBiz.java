package com.lyranxi.link.user.biz;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lyranxi.link.mysql.biz.BaseBiz;
import com.lyranxi.link.user.bo.TenantCreateBO;
import com.lyranxi.link.user.converter.TenantConverter;
import com.lyranxi.link.user.entity.Tenant;
import com.lyranxi.link.user.mapper.TenantMapper;
import com.lyranxi.link.user.token.UserTenantInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 租户biz
 *
 * @author ranxi
 * @date 2025-04-09 18:23
 */
@Slf4j
@Service
public class TenantBiz extends BaseBiz<TenantMapper, Tenant> {

    /**
     * 查询租户下所有品牌和门店
     *
     * @param tenantId 租户ID
     * @return List<UserTenantInfo>
     */
    public Set<UserTenantInfo> getTenantAllStores(String tenantId) {
        return this.baseMapper.getTenantAllStores(tenantId);
    }

    /**
     * 查询品牌下所有门店
     *
     * @param brandId 品牌ID
     * @return List<UserTenantInfo> 品牌下的所有门店
     */
    public Set<UserTenantInfo> getBrandAllStores(String brandId) {
        return this.baseMapper.getBrandAllStores(brandId);
    }

    /**
     * 查询门店信息
     *
     * @param storeId 门店ID
     * @return UserTenantInfo 门店信息
     */
    public Set<UserTenantInfo> getStore(String storeId) {
        return CollectionUtil.newHashSet(this.baseMapper.getStore(storeId));
    }

    /**
     * 根据电话号码查询租户信息
     *
     * @param phone 电话号码
     * @return 租户信息
     */
    public Tenant getByPhone(String phone) {
        return this.baseMapper.selectOne(Wrappers.<Tenant>lambdaQuery()
                .eq(Tenant::getPhone, phone)
                .eq(Tenant::getDeleted, Boolean.FALSE));
    }

    /**
     * 根据名称查询租户信息
     *
     * @param name 名称
     * @return 租户信息
     */
    public Tenant getByName(String name) {
        return this.baseMapper.selectOne(Wrappers.lambdaQuery(Tenant.class)
                .eq(Tenant::getName, name)
                .eq(Tenant::getDeleted, Boolean.FALSE));
    }

    /**
     * 创建租户
     *
     * @param params     租户信息
     * @param operatorId 创建用户ID
     * @return Tenant 租户信息
     */
    public Tenant create(TenantCreateBO params, Long operatorId) {
        Tenant tenant = TenantConverter.INSTANCE.to(params);
        tenant.setCreateBy(operatorId);
        this.insert(tenant);
        return tenant;
    }

}
