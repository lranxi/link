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
 * 角色分配用户
 *
 * @author ranxi
 * @date 2025-03-24 16:47
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleAssignUserBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @NotBlank(message = "角色ID不能为空")
    private String roleId;

    /**
     * 用户ID
     */
    @NotEmpty(message = "用户ID不能为空")
    private Set<String> userIds;

}
