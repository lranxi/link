package com.lyranxi.link.user.biz;

import cn.hutool.core.collection.CollectionUtil;
import com.lyranxi.link.mysql.biz.BaseBiz;
import com.lyranxi.link.user.bo.StoreCreateBO;
import com.lyranxi.link.user.converter.StoreConverter;
import com.lyranxi.link.user.entity.Store;
import com.lyranxi.link.user.mapper.StoreMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ranxi
 * @date 2025-04-09 18:23
 */
@Slf4j
@Service
public class StoreBiz extends BaseBiz<StoreMapper, Store> {

    public int getTenantCreatedStoreCount(String tenantId) {
        return CollectionUtil.size(this.baseMapper.selectTenantCreatedStoreCount(tenantId));
    }

    public boolean create(StoreCreateBO params, Long operatorId) {
        Store store = StoreConverter.INSTANCE.to(params);
        store.setCreateBy(operatorId);
        return this.insert(store);
    }

}
