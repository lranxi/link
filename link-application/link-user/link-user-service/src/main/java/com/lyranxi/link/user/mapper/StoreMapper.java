package com.lyranxi.link.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyranxi.link.user.entity.Store;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author ranxi
* @description 针对表【store(门店信息)】的数据库操作Mapper
* @createDate 2025-04-09 18:12:26
* @Entity com.lyranxi.link.user.entity.Store
*/
public interface StoreMapper extends BaseMapper<Store> {

    List<Store> selectTenantCreatedStoreCount(@Param("tenantId")String tenantId);

}




