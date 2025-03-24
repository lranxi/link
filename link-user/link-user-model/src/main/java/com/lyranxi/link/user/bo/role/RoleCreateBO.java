package com.lyranxi.link.user.bo.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 创建角色
 * @author ranxi
 * @date 2025-03-24 15:42
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleCreateBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 租户ID */
    @NotNull(message = "租户ID不能为空")
    private Long tenantId;

    /** 门店ID */
    private Long storeId;

    /** 角色名称 */
    @NotBlank(message = "角色名称不能为空")
    @Size(min = 1,max = 20,message = "角色名称长度不能超过20个字符")
    private String roleName;

    /** 是否为系统角色 */
    @NotNull(message = "请选择是否属于系统角色")
    private Boolean system;

}
