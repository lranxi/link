package com.lyranxi.link.user.token;

import cn.hutool.core.collection.CollectionUtil;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

/**
 * UserToken
 *
 * @author ranxi
 * @date 2025-04-11 14:49
 */
@Data
@ToString
public class UserToken implements Serializable {

    /**
     * 用户ID
     */
    private final Long userId;
    /**
     * 用户名
     */
    private final String username;

    /**
     * 用户昵称
     */
    private final String nickname;

    /**
     * 登录平台
     */
    private final Integer platform;
    /**
     * 客户端ID
     */
    private final Integer clientId;

    /**
     * 用户租户信息
     */
    private UserTenantInfo userTenant;


    /**
     * 拥有的api访问权限
     */
    private Set<Long> apiIds;


    public UserToken(Long userId, String username, String nickname, Integer platform, Integer clientId) {
        this.userId = userId;
        this.username = username;
        this.nickname = nickname;
        this.platform = platform;
        this.clientId = clientId;
    }

    /**
     * 检查时候拥有的api访问权限
     *
     * @param apiId apiId
     * @return boolean 是否拥有访问权限
     */
    public boolean hasApi(Long apiId) {
        return CollectionUtil.contains(apiIds, apiId);
    }


}
