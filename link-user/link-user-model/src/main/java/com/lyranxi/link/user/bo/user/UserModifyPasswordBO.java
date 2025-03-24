package com.lyranxi.link.user.bo.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author ranxi
 * @date 2025-03-24 17:37
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserModifyPasswordBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    private String uid;

    /**
     * 旧密码
     */
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;

    /**
     * 新密码
     */

    @Size(min = 6, max = 64, message = "新密码长度在6-64位之间")
    @NotBlank(message = "新密码不能为空")
    private String newPassword;

}
