package com.lyranxi.link.user.biz;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lyranxi.link.mysql.biz.BaseBiz;
import com.lyranxi.link.user.entity.User;
import com.lyranxi.link.user.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * userBiz
 *
 * @author ranxi
 * @date 2025-03-13 18:14
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
    public User getByUsername(Long tenantId, Long storeId, String username) {
        return this.baseMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getTenantId, tenantId)
                .eq(User::getStoreId, storeId)
                .eq(User::getUsername, username)
                .eq(User::getDeleted, Boolean.FALSE));
    }

    /**
     * 通过uid查询用户信息
     *
     * @param uid uid
     * @return 角色ID
     */
    public User getByUid(String uid) {
        return this.baseMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getUid, uid)
                .eq(User::getDeleted, Boolean.FALSE));
    }

    /**
     * 通过uid批量查询用户信息
     *
     * @param uidSet uid
     * @return List<User> 用户信息
     */
    public List<User> batchGetByUid(Set<String> uidSet) {
        return this.baseMapper.selectList(Wrappers.<User>lambdaQuery()
                .in(User::getUid, uidSet)
                .eq(User::getDeleted, Boolean.FALSE));
    }

}
