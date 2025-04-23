package com.lyranxi.link.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyranxi.link.user.entity.Revision;
import org.apache.ibatis.annotations.Param;

/**
 * @author ranxi
 * @date 2025-04-21 16:38
 */
public interface RevisionMapper extends BaseMapper<Revision> {

    /**
     * 逻辑删除
     *
     * @param revisionId 版本ID
     * @param operatorId 操作用户ID
     * @return int 影响行数
     */
    int logicDeleteByRevisionId(@Param("revisionId") Long revisionId, @Param("operatorId") Long operatorId);

}
