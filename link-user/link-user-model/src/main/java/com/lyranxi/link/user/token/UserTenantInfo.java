package com.lyranxi.link.user.token;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 员工租户信息
 * @author ranxi
 * @date 2025-04-07 18:16
 */
@Data
@NoArgsConstructor
public class UserTenantInfo implements Serializable {
    /** 用户ID */
    private Long userId;
    /** 昵称 */
    private String nickname;
    /** 租户ID */
    private String tenantId;
    /** 租户名称 */
    private String tenantName;

    /** 门店ID */
    private String storeId;
    /** 门店名称 */
    private String storeName;

    /** 租户类型 */
    private Integer tenantType;
    /** 是否系统管理员 */
    private Boolean system;
}
