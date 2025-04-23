package com.lyranxi.link.user.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

/**
 * 角色编辑BO
 *
 * @author ranxi
 * @date 2025-04-21 14:09
 */
@Data
public class RoleUpdateBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @NotNull(message = "角色ID不能为空")
    public Long id;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    /**
     * 菜单ID
     */
    @NotEmpty(message = "菜单不能为空")
    private Set<Long> menuIds;

}
