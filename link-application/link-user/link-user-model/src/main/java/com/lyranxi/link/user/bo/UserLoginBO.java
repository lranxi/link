package com.lyranxi.link.user.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 用户登录BO
 *
 * @author ranxi
 * @date 2025-04-11 15:37
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserLoginBO extends AbstractUserLoginBO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @NotBlank(message = "登录账号不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "登录密码不能为空")
    private String password;

}
