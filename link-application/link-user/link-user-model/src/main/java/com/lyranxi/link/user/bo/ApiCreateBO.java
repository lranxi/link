package com.lyranxi.link.user.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author ranxi
 * @date 2025-04-10 18:02
 */
@Data
public class ApiCreateBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * api名称
     */
    @NotBlank(message = "api名称不能为空")
    private String name;

    /**
     * api路径
     */
    @NotBlank(message = "api路径不能为空")
    private String url;

    /**
     * 请求方式
     */
    @NotNull(message = "请求方式不能为空")
    private String method;

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
