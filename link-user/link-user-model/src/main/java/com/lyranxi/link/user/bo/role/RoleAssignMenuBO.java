package com.lyranxi.link.user.bo.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;


/**
 * 角色分配权限
 * @author ranxi
 * @date 2025-03-24 16:32
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleAssignMenuBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @NotBlank(message = "角色ID不能为空")
    private String roleId;

    /**
     * 权限ID
     */
    @NotEmpty(message = "权限ID不能为空")
    private Set<String> menuIds;

}
