package com.lyranxi.link.user.bo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author ranxi
 * @date 2025-03-24 17:50
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAssignRoleBO implements Serializable {

    @Serial
    private final static long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    private Long userId;

    /**
     * 角色ID
     */
    @NotBlank(message = "角色ID不能为空")
    private Long roleId;

}
