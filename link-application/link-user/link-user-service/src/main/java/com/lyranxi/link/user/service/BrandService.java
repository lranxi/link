package com.lyranxi.link.user.service;

import com.lyranxi.link.common.bo.CoreBO;
import com.lyranxi.link.common.constant.GlobalConstant;
import com.lyranxi.link.common.exception.BusinessException;
import com.lyranxi.link.common.util.asserts.AssertBusinessException;
import com.lyranxi.link.user.biz.BrandBiz;
import com.lyranxi.link.user.biz.TenantRevisionRelateBiz;
import com.lyranxi.link.user.bo.BrandCreateBO;
import com.lyranxi.link.user.entity.TenantRevisionRelate;
import com.lyranxi.link.user.feign.BrandFeign;
import com.lyranxi.link.web.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author ranxi
 * @date 2025-04-23 14:38
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class BrandService implements BrandFeign {

    private final BrandBiz brandBiz;
    private final TenantRevisionRelateBiz tenantRevisionRelateBiz;


    @Override
    public Result<Boolean> create(CoreBO<BrandCreateBO> params) {
        BrandCreateBO content = params.getContent();
        // 检查租户的品牌数量
        this.checkBrandCount(content.getTenantId());
        // 检查名称
        this.checkBrandNameOnCreate(content.getTenantId(), content.getName());
        // 创建品牌
        brandBiz.create(content, params.getOperatorId());

        return Result.success(Boolean.TRUE);
    }

    /**
     * 检查租户的品牌数量
     *
     * @param tenantId 租户ID
     */
    private void checkBrandCount(String tenantId) {
        TenantRevisionRelate relate = tenantRevisionRelateBiz.getByTenantId(tenantId);
        if (Objects.isNull(relate)) {
            log.warn("{} 租户版本关联关系为空. tenantId: {}", GlobalConstant.IMPORTANT_SYMBOL, tenantId);
            throw new BusinessException("数据异常,请联系技术支持");
        }
        // 查询已经创建的租户数量
        int count = brandBiz.getTenantCreatedBrandCount(tenantId);
        if (relate.getBrandNum() <= count) {
            throw new BusinessException("超出可创建品牌数量");
        }
    }

    /**
     * 检查品牌名称是否已经存在
     *
     * @param tenantId 租户ID
     * @param brandName 品牌名称
     */
    private void checkBrandNameOnCreate(String tenantId, String brandName) {
        AssertBusinessException.isNull(brandBiz.getByTenantIdAndName(tenantId, brandName), "品牌名称已经存在");
    }

}
