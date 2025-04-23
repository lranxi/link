package com.lyranxi.link.user.biz;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lyranxi.link.mysql.biz.BaseBiz;
import com.lyranxi.link.user.entity.TenantRevisionRelate;
import com.lyranxi.link.user.mapper.TenantRevisionRelateMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ranxi
 * @date 2025-04-21 18:10
 */
@Slf4j
@Service
public class TenantRevisionRelateBiz extends BaseBiz<TenantRevisionRelateMapper, TenantRevisionRelate> {

    /**
     * 根据版本查询正在使用的租户列表
     *
     * @param revisionId 版本ID
     * @return List<TenantRevisionRelate>
     */
    public List<TenantRevisionRelate> getByRevisionId(Long revisionId) {
        return this.baseMapper.selectList(Wrappers.lambdaQuery(TenantRevisionRelate.class)
                .eq(TenantRevisionRelate::getRevisionId, revisionId)
                .orderByDesc(true, TenantRevisionRelate::getCreateBy));
    }

    /**
     * 查询有效 绑定关系
     *
     * @param revisionId 版本ID
     * @return List<TenantRevisionRelate>
     */
    public List<TenantRevisionRelate> getValidByRevisionId(Long revisionId) {
        List<TenantRevisionRelate> relates = this.getByRevisionId(revisionId);

        return Optional.ofNullable(relates).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item.getEndTime().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    public boolean create(String tenantId, Long revisionId, Integer brandNum, Integer storeNum, LocalDateTime expireTime, Long operatorId) {
        TenantRevisionRelate relate = new TenantRevisionRelate();
        relate.setTenantId(tenantId);
        relate.setRevisionId(revisionId);
        relate.setBrandNum(brandNum);
        relate.setStoreNum(storeNum);
        relate.setBeginTime(LocalDateTime.now());
        relate.setEndTime(expireTime);
        relate.setCreateBy(operatorId);
        return this.insert(relate);
    }


    /**
     * 查询租户的品牌数量
     *
     * @param tenantId 租户ID
     * @return 已经存在的品牌数量
     */
    public TenantRevisionRelate getByTenantId(String tenantId) {
        return this.baseMapper.selectOne(Wrappers.lambdaQuery(TenantRevisionRelate.class)
                .eq(TenantRevisionRelate::getTenantId, tenantId));
    }

}
