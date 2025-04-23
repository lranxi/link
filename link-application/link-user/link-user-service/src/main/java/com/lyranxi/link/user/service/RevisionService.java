package com.lyranxi.link.user.service;

import com.lyranxi.link.common.bo.CoreBO;
import com.lyranxi.link.common.util.asserts.AssertBusinessException;
import com.lyranxi.link.user.biz.RevisionBiz;
import com.lyranxi.link.user.biz.RoleBiz;
import com.lyranxi.link.user.biz.RoleMenuRelateBiz;
import com.lyranxi.link.user.biz.TenantRevisionRelateBiz;
import com.lyranxi.link.user.bo.RevisionCreateBO;
import com.lyranxi.link.user.bo.RevisionUpdateBO;
import com.lyranxi.link.user.converter.RevisionConverter;
import com.lyranxi.link.user.entity.Revision;
import com.lyranxi.link.user.entity.Role;
import com.lyranxi.link.user.entity.TenantRevisionRelate;
import com.lyranxi.link.user.feign.RevisionFeign;
import com.lyranxi.link.web.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ranxi
 * @date 2025-04-21 16:59
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class RevisionService implements RevisionFeign {

    private final RoleBiz roleBiz;
    private final RevisionBiz revisionBiz;
    private final RoleMenuRelateBiz roleMenuRelateBiz;
    private final TenantRevisionRelateBiz tenantRevisionRelateBiz;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<Boolean> create(CoreBO<RevisionCreateBO> params) {
        RevisionCreateBO content = params.getContent();
        // 创建模板角色
        Role role = roleBiz.createRevisionTemplateRole(content.getName(), params.getOperatorId());
        // 角色菜单关联关系
        roleMenuRelateBiz.insertRelates(role.getId(), content.getMenuIds(), params.getOperatorId());
        // 创建版本
        revisionBiz.create(content, role.getId(), params.getOperatorId());
        return Result.success(Boolean.TRUE);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<Boolean> update(CoreBO<RevisionUpdateBO> params) {
        Revision existRevision = revisionBiz.assertRevisionExist(params.getContent().getId());

        // 更新菜单
        roleMenuRelateBiz.insertRelates(existRevision.getTemplateRoleId(), params.getContent().getMenuIds(), params.getOperatorId());

        // 编辑版本
        Revision revision = RevisionConverter.INSTANCE.to(params.getContent());
        revision.setUpdateBy(params.getOperatorId());
        revision.setTemplateRoleId(existRevision.getTemplateRoleId());
        return Result.success(revisionBiz.updateByPrimaryKey(revision));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<Boolean> delete(CoreBO<Long> params) {
        Revision revision = revisionBiz.assertRevisionExist(params.getContent());
        // 检查是否有门店在使用
        List<TenantRevisionRelate> relates = tenantRevisionRelateBiz.getValidByRevisionId(revision.getId());
        AssertBusinessException.isEmpty(relates, "当前版本存在正在使用的租户");

        // 删除关联关系
        roleMenuRelateBiz.deleteByRoleId(revision.getTemplateRoleId());

        // 删除版本
        return Result.success(revisionBiz.logicDeleteById(revision.getId(), params.getOperatorId()));
    }

    @Override
    public Result<Boolean> switchEnable(CoreBO<Long> params) {
        Revision revision = revisionBiz.assertRevisionExist(params.getContent());
        revision.setEnable(!revision.getEnable());
        revision.setUpdateBy(params.getOperatorId());
        return Result.success(revisionBiz.updateByPrimaryKey(revision));
    }
}
