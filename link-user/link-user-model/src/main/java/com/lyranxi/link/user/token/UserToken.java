package com.lyranxi.link.user.token;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

/**
 * @author ranxi
 * @date 2025-04-07 18:16
 */
@Getter
@ToString
public class UserToken implements Serializable {

    private final String username;
    private final Set<String> uids;
    private final Integer platform;

    @Setter
    private UserTenantInfo userTenant;

    @Setter
    private Integer clientId;

    @Setter
    private Set<Long> apiIds;

    /**
     * 是否拥有api的访问权限
     *
     * @param apiId apiId
     * @return boolean  ture表示拥有此api权限
     */
    public boolean hasApi(Long apiId) {
        return apiIds != null && apiIds.contains(apiId);
    }

    /**
     * 是否可以登录
     *
     * @param uid 员工ID
     * @return boolean true表示可以登录
     */
    public boolean canLogin(String uid) {
        return uids != null && uids.contains(uid);
    }

    public UserToken(String username, Set<String> uids, Integer platform) {
        this.username = username;
        this.uids = uids;
        this.platform = platform;
    }
}
