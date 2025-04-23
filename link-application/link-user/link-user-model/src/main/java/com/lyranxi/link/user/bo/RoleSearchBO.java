package com.lyranxi.link.user.bo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色查询BO
 * @author ranxi
 * @date 2025-04-18 13:41
 */
@Data
public class RoleSearchBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String tenantId;

    private String brandId;

    private String storeId;

    private String roleName;

}
