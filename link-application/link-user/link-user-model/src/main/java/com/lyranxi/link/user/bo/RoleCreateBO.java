package com.lyranxi.link.user.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

/**
 * 角色创建BO
 * @author ranxi
 * @date 2025-04-21 11:27
 */
@Data
public class RoleCreateBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 租户ID */
    @NotNull(message = "租户ID不能为空")
    private String tenantId;

    /** 品牌ID */
    private String brandId;

    /** 门店id */
    private String storeId;

    /** 角色名称 */
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    /** 菜单ID */
    @NotEmpty(message = "角色ID不能为空")
    private Set<Long> menuIds;

}
