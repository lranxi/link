package com.lyranxi.link.user.service;

import com.lyranxi.link.common.bo.CoreBO;
import com.lyranxi.link.common.constant.GlobalConstant;
import com.lyranxi.link.common.exception.BusinessException;
import com.lyranxi.link.user.biz.StoreBiz;
import com.lyranxi.link.user.biz.TenantRevisionRelateBiz;
import com.lyranxi.link.user.bo.StoreCreateBO;
import com.lyranxi.link.user.entity.TenantRevisionRelate;
import com.lyranxi.link.user.feign.StoreFeign;
import com.lyranxi.link.web.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author ranxi
 * @date 2025-04-23 15:19
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class StoreService implements StoreFeign {

    private final StoreBiz storeBiz;
    private final TenantRevisionRelateBiz tenantRevisionRelateBiz;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<Boolean> create(CoreBO<StoreCreateBO> params) {
        StoreCreateBO content = params.getContent();
        // 检查可创建数量
        this.checkBrandCount(content.getTenantId());
        // 创建门店
        storeBiz.create(content, params.getOperatorId());
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
        int count = storeBiz.getTenantCreatedStoreCount(tenantId);
        if (relate.getStoreNum() <= count) {
            throw new BusinessException("超出可创建门店数量");
        }
    }

}
