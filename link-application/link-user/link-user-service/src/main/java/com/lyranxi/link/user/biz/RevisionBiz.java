package com.lyranxi.link.user.biz;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.lyranxi.link.common.util.asserts.AssertRequestParameter;
import com.lyranxi.link.mysql.biz.BaseBiz;
import com.lyranxi.link.user.bo.RevisionCreateBO;
import com.lyranxi.link.user.converter.RevisionConverter;
import com.lyranxi.link.user.entity.Revision;
import com.lyranxi.link.user.mapper.RevisionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ranxi
 * @date 2025-04-21 16:37
 */
@Slf4j
@Service
public class RevisionBiz extends BaseBiz<RevisionMapper, Revision> {

    /**
     * 创建版本
     *
     * @param params     版本信息
     * @param operatorId 操作人ID
     * @return 是否创建成功
     */
    public Boolean create(RevisionCreateBO params, Long templateRoleId, Long operatorId) {
        Revision revision = RevisionConverter.INSTANCE.to(params);
        revision.setTemplateRoleId(templateRoleId);
        revision.setCreateBy(operatorId);
        return this.insert(revision);
    }


    /**
     * 断言版本存在
     *
     * @param id 版本ID
     * @return Revision 版本信息
     */
    public Revision assertRevisionExist(Long id) {
        Revision revision = this.baseMapper.selectOne(Wrappers.lambdaQuery(Revision.class)
                .eq(Revision::getId, id)
                .eq(Revision::getDeleted, Boolean.FALSE));
        AssertRequestParameter.nonNull(revision, "版本不存在");
        return revision;
    }

    /**
     * 逻辑删除
     *
     * @param revisionId 版本ID
     * @param operatorId 操作用户ID
     * @return boolean 是否删除成功
     */
    public boolean logicDeleteById(Long revisionId, Long operatorId) {
        return SqlHelper.retBool(this.baseMapper.logicDeleteByRevisionId(revisionId, operatorId));
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return Revision 版本信息
     */
    public Revision getById(Long id) {
        return this.baseMapper.selectOne(Wrappers.lambdaQuery(Revision.class)
                .eq(Revision::getId, id)
                .eq(Revision::getDeleted, Boolean.FALSE));
    }

}
