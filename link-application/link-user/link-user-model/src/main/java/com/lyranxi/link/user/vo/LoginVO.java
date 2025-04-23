package com.lyranxi.link.user.vo;

import com.lyranxi.link.user.token.UserTenantInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * 登录结果
 *
 * @author ranxi
 * @date 2025-04-11 15:50
 */
@Data
public class LoginVO implements Serializable {

    /**
     * token
     */
    private String token;

    /** 用户信息 */
    private UserSimpleInfoVO userInfo;

    /**
     * 可登录的租户信息
     */
    private Set<UserTenantInfo> userTenants;

    /**
     * 当前登录的租户信息
     */
    private UserTenantInfo currentTenant;

}
