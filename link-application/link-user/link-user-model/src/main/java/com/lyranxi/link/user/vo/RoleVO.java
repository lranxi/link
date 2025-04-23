package com.lyranxi.link.user.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ranxi
 * @date 2025-04-18 13:37
 */
@Data
public class RoleVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 租户ID */
    private String tenantId;
    /** 租户名称 */
    private String tenantName;
    /** 品牌ID */
    private String brandId;
    /** 品牌名称 */
    private String brandName;
    /** 门店id */
    private String storeId;
    /** 门店名称 */
    private String storeName;
    /** 角色名称 */
    private String roleName;
    /** 角色权限范围 */
    private Integer roleScope;
    /** 是否系统角色 */
    private Boolean system;
    /** 状态 */
    private Boolean enable;
    /** 创建时间 */
    private LocalDateTime createTime;
}
