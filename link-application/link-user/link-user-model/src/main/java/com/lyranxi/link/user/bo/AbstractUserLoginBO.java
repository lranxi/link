package com.lyranxi.link.user.bo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author ranxi
 * @date 2025-04-11 15:39
 */
@Data
public class AbstractUserLoginBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "登录方式不能为空")
    private Integer loginType;

    /**
     * 登录平台
     */
    @NotNull(message = "登录平台不能为空")
    private Integer platform;

    /**
     * 客户端ID
     */
    @NotNull(message = "客户端ID不能为空")
    private Integer clientId;

}
