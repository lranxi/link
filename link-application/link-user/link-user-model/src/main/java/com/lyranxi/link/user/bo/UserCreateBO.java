package com.lyranxi.link.user.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author ranxi
 * @date 2025-04-15 11:46
 */
@Data
public class UserCreateBO implements Serializable {

    /**
     * 租户ID
     */
    @NotNull(message = "租户ID不能为空")
    private String tenantId;


    private String brandId;

    /**
     * 门店ID
     */
    private String storeId;

    /**
     * 登录账号
     */
    @Size(min = 4, max = 64, message = "用户名长度最小为6位，最大为64位")
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 用户昵称
     */
    @Size(min = 2, max = 64, message = "昵称长度最小为2位，最大为64位")
    private String nickname;

    /**
     * 头像地址
     */
    @Size(max = 150, message = "头像地址长度最大为150位")
    private String avatar;

    /**
     * 电话号码
     */
    @Size(min = 6, max = 20, message = "手机号码长度最小为6位，最大为20位")
    @NotBlank(message = "手机号码不能为空")
    private String phone;

    /**
     * 密码
     */
    @Size(min = 6, max = 64, message = "密码长度最小为6位，最大为64位")
    private String password;

    /** 用户角色 */
    @NotNull(message = "请选择用户角色")
    private Long roleId;

    /**
     * 是否为系统用户
     */
    @NotNull(message = "是否为系统用户不能为空")
    private Boolean system;

    @Serial
    private static final long serialVersionUID = 1L;

}
