package com.lyranxi.link.user.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.lyranxi.link.common.bo.CoreBO;
import com.lyranxi.link.common.constant.GlobalConstant;
import com.lyranxi.link.common.exception.BusinessException;
import com.lyranxi.link.common.util.asserts.AssertBusinessException;
import com.lyranxi.link.user.biz.RoleBiz;
import com.lyranxi.link.user.biz.UserBiz;
import com.lyranxi.link.user.biz.UserRoleRelateBiz;
import com.lyranxi.link.user.bo.UserAssignRoleBO;
import com.lyranxi.link.user.bo.UserCreateBO;
import com.lyranxi.link.user.bo.UserUpdateBO;
import com.lyranxi.link.user.constant.UserConstant;
import com.lyranxi.link.user.converter.UserConverter;
import com.lyranxi.link.user.entity.Role;
import com.lyranxi.link.user.entity.User;
import com.lyranxi.link.user.feign.UserFeign;
import com.lyranxi.link.user.utils.PasswordUtil;
import com.lyranxi.link.web.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author ranxi
 * @date 2025-04-14 14:33
 */
@Slf4j
@Service
@RestController
@RequiredArgsConstructor
public class UserService implements UserFeign {

    private final UserBiz userBiz;
    private final RoleBiz roleBiz;
    private final UserRoleRelateBiz userRoleRelateBiz;

    @Override
    public Result<Boolean> create(CoreBO<UserCreateBO> params) {
        UserCreateBO userParam = params.getContent();
        this.assertPhoneUnique(userParam.getPhone());
        this.assertUsernameUnique(userParam.getTenantId(), userParam.getStoreId(), userParam.getUsername());

        // 保存用户信息
        User User = this.formatEntity(userParam, params.getOperatorId());
        userBiz.insert(User);

        // 分配角色
        UserAssignRoleBO assignRoleParams = UserAssignRoleBO.builder()
                .userId(User.getId())
                .roleId(userParam.getRoleId())
                .build();
        this.assignRole(assignRoleParams, params.getOperatorId());
        return Result.success(Boolean.TRUE);
    }


    @Override
    public Result<Boolean> update(CoreBO<UserUpdateBO> params) {
        UserUpdateBO content = params.getContent();
        User user = assertUserExist(content.getId());
        user.setTenantId(content.getTenantId());
        user.setBrandId(content.getBrandId());
        user.setStoreId(content.getStoreId());
        user.setPhone(content.getPhone());
        user.setAvatar(StrUtil.isBlank(content.getAvatar()) ? GlobalConstant.DEFAULT_USER_AVATAR : content.getAvatar());
        user.setUpdateBy(params.getOperatorId());
        return Result.success(userBiz.updateByPrimaryKey(user));
    }

    @Override
    public Result<Boolean> delete(CoreBO<Long> params) {
        return Result.success(userBiz.logicDeleteById(params.getContent(), params.getOperatorId()));
    }

    @Override
    public Result<Boolean> switchEnable(CoreBO<Long> params) {
        User user = this.assertUserExist(params.getContent());
        user.setEnable(!user.getEnable());
        user.setUpdateBy(params.getOperatorId());
        return Result.success(userBiz.updateByPrimaryKey(user));
    }

    @Override
    public Result<Boolean> restPassword(CoreBO<Long> params) {

        User user = this.assertUserExist(params.getContent());

        user.setPasswordHash(PasswordUtil.encrypt(UserConstant.DEFAULT_PASSWORD, user.getSalt()));
        user.setUpdateBy(params.getOperatorId());
        return Result.success(userBiz.updateByPrimaryKey(user));
    }

    @Override
    public Result<Boolean> assignRole(CoreBO<UserAssignRoleBO> params) {
        return Result.success(this.assignRole(params.getContent(), params.getOperatorId()));
    }

    /**
     * 用户分配角色
     *
     * @param params uid、roleId
     * @return 是否操作成功
     */
    public boolean assignRole(UserAssignRoleBO params, Long operatorId) {
        User user = this.assertUserExist(params.getUserId());
        Role role = roleBiz.assertRoleExist(params.getRoleId());
        if (!Objects.equals(user.getSystem(), role.getSystem())) {
            if (user.getSystem()) {
                throw new BusinessException("系统用户只能分配系统角色");
            } else {
                throw new BusinessException("租户用户只能分配租户角色");
            }
        }
        return userRoleRelateBiz.insertRelates(params.getRoleId(), CollectionUtil.newHashSet(user.getId()), operatorId);
    }

    private User formatEntity(UserCreateBO params, Long operatorId) {
        User user = UserConverter.INSTANCE.to(params);
        String salt = RandomUtil.randomString(16);
        if (StrUtil.isBlank(user.getNickname())) {
            params.setNickname("用户" + params.getPhone().substring(params.getPhone().length() - 4));
        }
        user.setPasswordHash(PasswordUtil.encrypt(params.getPassword(), salt));
        user.setSalt(salt);
        user.setCreateBy(operatorId);
        return user;
    }


    /**
     * 断言用户存在
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    private User assertUserExist(Long userId) {
        User user = userBiz.getById(userId);
        AssertBusinessException.nonNull(user, "用户不存在或已删除");
        return user;
    }

    /**
     * 断言用户名唯一
     *
     * @param tenantId 租户ID
     * @param storeId  门店ID
     * @param username 用户名称
     * @throws BusinessException 用户名已经存在
     */
    private void assertUsernameUnique(String tenantId, String storeId, String username) {
        User user = userBiz.getByUsername(tenantId, storeId, username);
        AssertBusinessException.isNull(user, "用户名已经存在");
    }

    /**
     * 断言电话号码唯一
     *
     * @param phone 电话号码
     * @throws BusinessException 电话号码已经被注册
     */
    private void assertPhoneUnique(String phone) {
        User user = userBiz.getByPhone(phone);
        AssertBusinessException.isNull(user, "电话号码已经被注册");
    }

}
