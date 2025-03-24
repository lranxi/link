package com.lyranxi.link.user.bo.permission;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 权限保存BO
 *
 * @author ranxi
 * @date 2025-03-24 18:17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionCreateBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * api名称
     */
    @NotBlank(message = "api名称不能为空")
    private String apiName;

    /**
     * api路径
     */
    @NotBlank(message = "api路径不能为空")
    private String apiPath;

    /**
     * 请求方式
     */
    @NotNull(message = "请求方式不能为空")
    private Integer apiMethod;


    /**
     * 是否白名单(白名单接口不校验权限)
     */
    @NotNull(message = "请选择是否白名单")
    private Boolean whitelist;

    /**
     * 适用范围(1:运营后台/2: 代理商后台/3: 租户后台/4: 商家app/5: 用户小程序)
     */
    @NotBlank(message = "适用范围不能为空")
    @Pattern(regexp = "[1-6]")
    private Integer scope;

    /**
     * 接口作者
     */
    @NotBlank(message = "作者不能为空")
    private String author;

}
