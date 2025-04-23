package com.lyranxi.link.user.biz;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.lyranxi.link.common.util.asserts.AssertBusinessException;
import com.lyranxi.link.mysql.biz.BaseBiz;
import com.lyranxi.link.user.constant.UserConstant;
import com.lyranxi.link.user.entity.User;
import com.lyranxi.link.user.mapper.UserMapper;
import com.lyranxi.link.user.utils.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ranxi
 * @date 2025-04-09 18:24
 */
@Slf4j
@Service
public class UserBiz extends BaseBiz<UserMapper, User> {

    /**
     * 根据电话号码查询用户信息
     *
     * @param phone 手机号码
     * @return User 用户信息
     */
    public User getByPhone(String phone) {
        return this.baseMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getPhone, phone)
                .eq(User::getDeleted, Boolean.FALSE));
    }

    /**
     * 根据用户名查询用户信息
     *
     * @param tenantId 租户ID
     * @param storeId  门店ID
     * @param username 用户名称
     * @return User 用户信息
     */
    public User getByUsername(String tenantId, String storeId, String username) {
        return this.baseMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getTenantId, tenantId)
                .eq(User::getStoreId, storeId)
                .eq(User::getUsername, username)
                .eq(User::getDeleted, Boolean.FALSE));
    }

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return User 用户信息
     */
    public User getByUsername(String username) {
        return this.baseMapper.selectByUsername(username);
    }

    /**
     * 根据ID查询用户信息
     *
     * @param id 用户ID
     * @return User 用户信息
     */
    public User getById(Long id) {
        return this.baseMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getId, id)
                .eq(User::getDeleted, Boolean.FALSE));
    }

    /**
     * 断言用户ID有效
     *
     * @param id 用户ID
     * @return User 用户信息
     */
    public User assertUserIdValid(Long id) {
        User user = this.getById(id);
        AssertBusinessException.nonNull(user, "用户不存在或已经删除");
        return user;
    }

    /**
     * 删除用户信息
     *
     * @param id         用户ID
     * @param operatorId 操作用户ID
     * @return boolean 是否删除成功
     */
    public boolean logicDeleteById(Long id, Long operatorId) {
        return SqlHelper.retBool(this.baseMapper.logicDeleteByUserId(id, operatorId));
    }


    /**
     * 创建租户管理员
     *
     * @param tenantId   租户ID
     * @param phone      电话号码
     * @param operatorId 操作用户id
     * @return User 用户信息
     */
    public User createTenantAdminUser(String tenantId, String phone, Long operatorId) {
        String salt = RandomUtil.randomString(16);
        User user = new User();
        user.setTenantId(tenantId);
        user.setUsername(phone);
        user.setNickname("管理员");
        user.setPhone(phone);
        user.setSalt(salt);
        user.setPasswordHash(PasswordUtil.encrypt(UserConstant.DEFAULT_PASSWORD, salt));
        user.setCreateBy(operatorId);
        this.insert(user);
        return user;
    }

}
