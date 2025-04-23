package com.lyranxi.link.user.mapper;

import com.lyranxi.link.user.bo.ApiSearchBO;
import com.lyranxi.link.user.entity.Api;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyranxi.link.user.vo.ApiVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author ranxi
* @description 针对表【api(接口信息)】的数据库操作Mapper
* @createDate 2025-04-09 18:12:26
* @Entity com.lyranxi.link.user.entity.Api
*/
public interface ApiMapper extends BaseMapper<Api> {

    /**
     * 查询所有接口信息
     * @return List<Api> 接口信息
     */
    List<Api> selectAll();

    /**
     * 根据ID批量更新api信息
     * @param apis apis 接口信息
     * @return 影响行数
     */
    int batchUpdateById(@Param("apis")List<Api> apis);

    List<ApiVO> selectByCondition(@Param("params")ApiSearchBO params);

}




