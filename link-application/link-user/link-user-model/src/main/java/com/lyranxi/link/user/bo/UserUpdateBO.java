package com.lyranxi.link.user.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户编辑BO
 *
 * @author ranxi
 * @date 2025-04-21 15:28
 */
@Data
public class UserUpdateBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "用户id不能为空")
    private Long id;

    @NotBlank(message = "租户ID不能为空")
    private String tenantId;

    private String brandId;

    private String storeId;

    @NotBlank(message = "电话号码不能为空")
    private String phone;

    @NotBlank(message = "昵称不能为空")
    private String nickname;

    private String avatar;

}
