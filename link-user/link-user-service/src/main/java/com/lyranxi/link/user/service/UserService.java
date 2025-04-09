package com.lyranxi.link.user.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.lyranxi.link.common.exception.BusinessException;
import com.lyranxi.link.common.util.asserts.AssertBusinessException;
import com.lyranxi.link.common.util.uuid.UuidUtil;
import com.lyranxi.link.user.biz.RoleBiz;
import com.lyranxi.link.user.biz.UserBiz;
import com.lyranxi.link.user.biz.UserRoleMappingBiz;
import com.lyranxi.link.user.bo.user.UserAssignRoleBO;
import com.lyranxi.link.user.bo.user.UserModifyPasswordBO;
import com.lyranxi.link.user.bo.user.UserRegisterBO;
import com.lyranxi.link.user.constant.UserConstant;
import com.lyranxi.link.user.entity.Role;
import com.lyranxi.link.user.entity.User;
import com.lyranxi.link.user.utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


/**
 * UserService
 *
 * @author ranxi
 * @date 2025-03-13 18:16
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserBiz userBiz;
    private final RoleBiz roleBiz;
    private final UserRoleMappingBiz userRoleMappingBiz;

    @Transactional(rollbackFor = Exception.class)
    public boolean signup(UserRegisterBO params) {
        this.assertPhoneUnique(params.getPhone());
        this.assertUsernameUnique(params.getTenantId(), params.getStoreId(), params.getUsername());

        // 保存用户信息
        User User = this.signupBO2User(params);
        userBiz.insert(User);

        // 分配角色
        UserAssignRoleBO assignRoleParams = UserAssignRoleBO.builder()
                .uid(User.getUid())
                .roleId(params.getRoleId())
                .build();
        this.assignRole(assignRoleParams);
        return Boolean.TRUE;
    }


    /**
     * 重置用户密码
     *
     * @param uid 用户ID
     * @return 操作是否成功
     */
    public boolean resetPassword(String uid) {
        User user = this.assertUserExist(uid);

        user.setPasswordHash(PasswordUtil.encrypt(UserConstant.DEFAULT_PASSWORD, user.getSalt()));
        return userBiz.updateByPrimaryKey(user);
    }

    /**
     * 用户分配角色
     *
     * @param params uid、roleId
     * @return 是否操作成功
     */
    public boolean assignRole(UserAssignRoleBO params) {
        User user = this.assertUserExist(params.getUid());
        Role role = roleBiz.assertRoleExist(params.getRoleId());
        if (Objects.equals(user.getSystem(), role.getSystem())) {
            if (user.getSystem()) {
                throw new BusinessException("系统用户只能分配系统角色");
            } else {
                throw new BusinessException("租户用户只能分配租户角色");
            }
        }
        return userRoleMappingBiz.insertMappings(params.getRoleId(), CollectionUtil.newHashSet(user.getUid()));
    }


    /**
     * 修改密码
     *
     * @param params 用户ID、旧密码、新密码
     * @return 是否修改成功
     */
    public boolean modifyPassword(UserModifyPasswordBO params) {
        User user = this.assertUserExist(params.getUid());

        String oldPasswordEncrypt = PasswordUtil.encrypt(params.getOldPassword(), user.getSalt());
        if (!StrUtil.equals(oldPasswordEncrypt, user.getPasswordHash())) {
            throw new BusinessException("旧密码错误");
        }
        user.setPasswordHash(PasswordUtil.encrypt(params.getNewPassword(), user.getSalt()));
        return userBiz.updateByPrimaryKey(user);
    }


    /**
     * 断言用户存在
     *
     * @param uid 用户ID
     * @return 用户信息
     */
    public User assertUserExist(String uid) {
        User user = userBiz.getByUid(uid);
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
    private void assertUsernameUnique(Long tenantId, Long storeId, String username) {
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

    /**
     * 注册BO转换为数据库对象
     *
     * @param params 注册参数
     * @return User 数据库对象
     */
    private User signupBO2User(UserRegisterBO params) {
        if (StrUtil.isBlank(params.getNickname())) {
            params.setNickname("用户" + params.getPhone().substring(params.getPhone().length() - 4));
        }
        String salt = RandomUtil.randomString(16);
        User user = new User();
        user.setUid(UuidUtil.uuid22());
        user.setTenantId(params.getTenantId());
        user.setStoreId(params.getStoreId());
        user.setUsername(params.getUsername());
        user.setNickname(params.getNickname());
        user.setAvatar(params.getAvatar());
        user.setPhone(params.getPhone());
        user.setPasswordHash(PasswordUtil.encrypt(params.getPassword(), salt));
        user.setSalt(salt);
        user.setSystem(params.getSystem());
        user.setCreatedBy(params.getOperatorId());
        return user;
    }


}
