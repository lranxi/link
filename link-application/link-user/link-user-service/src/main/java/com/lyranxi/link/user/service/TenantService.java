package com.lyranxi.link.user.service;

import com.lyranxi.link.common.bo.CoreBO;
import com.lyranxi.link.common.constant.mq.TagConstant;
import com.lyranxi.link.common.util.asserts.AssertBusinessException;
import com.lyranxi.link.user.biz.*;
import com.lyranxi.link.user.bo.RoleCreateBO;
import com.lyranxi.link.user.bo.TenantCreateBO;
import com.lyranxi.link.user.entity.*;
import com.lyranxi.link.user.feign.TenantFeign;
import com.lyranxi.link.user.mq.producer.RocketMQProducer;
import com.lyranxi.link.web.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ranxi
 * @date 2025-04-23 11:40
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class TenantService implements TenantFeign {

    private final TenantBiz tenantBiz;
    private final UserBiz userBiz;
    private final RevisionBiz revisionBiz;
    private final RoleBiz roleBiz;
    private final RoleMenuRelateBiz roleMenuRelateBiz;
    private final UserRoleRelateBiz userRoleRelateBiz;
    private final TenantRevisionRelateBiz tenantRevisionRelateBiz;
    private final RocketMQProducer rocketMQProducer;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<Boolean> create(CoreBO<TenantCreateBO> params) {
        TenantCreateBO content = params.getContent();
        // 检查电话号码是否已经存在用户、是否已经绑定租户
        this.preCheckOnCreate(content);
        // 创建租户
        Tenant tenant = tenantBiz.create(content, params.getOperatorId());

        // 租户版本关联信息
        Revision revision = revisionBiz.getById(content.getRevisionId());
        tenantRevisionRelateBiz.create(tenant.getId(),
                content.getRevisionId(),
                revision.getBrandNum(),
                revision.getStoreNum(),
                content.getExpireTime(),
                params.getOperatorId());

        // 创建租户角色
        RoleCreateBO roleCreateBO = this.copyRevisionTemplateRole(tenant.getId(), content.getRevisionId());
        Role role = roleBiz.save(roleCreateBO, params.getOperatorId());

        // 创建租户管理员用户
        User user = userBiz.createTenantAdminUser(tenant.getId(), content.getPhone(), params.getOperatorId());

        // 关联管理员和角色
        userRoleRelateBiz.insertRelates(role.getId(), Set.of(user.getId()), params.getOperatorId());

        // 发送MQ
        rocketMQProducer.send(TagConstant.TENANT_CREATE, tenant);
        return Result.success(Boolean.TRUE);
    }

    private void preCheckOnCreate(TenantCreateBO params) {
        AssertBusinessException.isNull(tenantBiz.getByPhone(params.getPhone()), "电话号码已经绑定租户");
        AssertBusinessException.isNull(userBiz.getByPhone(params.getPhone()), "电话号码已经绑定用户");
        // 检查版本
        Revision revision = revisionBiz.getById(params.getRevisionId());
        AssertBusinessException.isTrue(revision.getEnable(), "版本已停用");
        // 检查名称是否重复
        AssertBusinessException.isNull(tenantBiz.getByName(params.getName()), "租户名称已经存在");
    }

    private RoleCreateBO copyRevisionTemplateRole(String tenantId, Long templateRoleId) {
        // 查询模板角色菜单
        List<RoleMenuRelate> relates = roleMenuRelateBiz.getByRoleId(templateRoleId);
        AssertBusinessException.nonEmpty(relates, "版本未配置权限");
        Set<Long> menuIds = relates.stream().map(RoleMenuRelate::getMenuId).collect(Collectors.toSet());

        RoleCreateBO params = new RoleCreateBO();
        params.setTenantId(tenantId);
        params.setRoleName("租户管理员");
        params.setMenuIds(menuIds);
        return params;
    }

}
