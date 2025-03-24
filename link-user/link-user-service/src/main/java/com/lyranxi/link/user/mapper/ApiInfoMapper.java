package com.lyranxi.link.user.mapper;

import com.lyranxi.link.user.entity.ApiInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author ranxi
* @description 针对表【apiInfo(接口信息表)】的数据库操作Mapper
* @createDate 2025-03-18 17:00:08
* @Entity com.lyranxi.link.user.entity.Permission
*/
public interface ApiInfoMapper extends BaseMapper<ApiInfo> {

    List<ApiInfo> selectAll();

    int batchUpdateById(@Param("permission") List<ApiInfo> apiInfos);

}




