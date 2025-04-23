package com.lyranxi.link.user.token;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ranxi
 * @date 2025-04-11 15:18
 */
@Data
@NoArgsConstructor
public class UserTenantInfo implements Serializable {

    /** 租户ID */
    private String tenantId;

    /** 租户名称 */
    private String tenantName;

    /** 品牌ID */
    private String brandId;

    /** 品牌名称 */
    private String brandName;

    /** 门店ID */
    private String storeId;

    /** 门店名称 */
    private String storeName;

    public UserTenantInfo(String tenantId, String tenantName) {
        this.tenantId = tenantId;
        this.tenantName = tenantName;
    }

    public UserTenantInfo(String tenantId, String tenantName, String brandId, String brandName) {
        this.tenantId = tenantId;
        this.tenantName = tenantName;
        this.brandId = brandId;
        this.brandName = brandName;
    }

    public UserTenantInfo(String tenantId, String tenantName, String brandId, String brandName, String storeId, String storeName) {
        this.tenantId = tenantId;
        this.tenantName = tenantName;
        this.brandId = brandId;
        this.brandName = brandName;
        this.storeId = storeId;
        this.storeName = storeName;
    }
}

