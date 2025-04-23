package com.lyranxi.link.user.bo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 租户创建 BO
 *
 * @author ranxi
 * @date 2025-04-23 11:00
 */
@Data
public class TenantCreateBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 租户名称
     */
    @NotBlank(message = "租户名称不能为空")
    @Size(min = 1, max = 100, message = "租户名称长度不能超过100个字符")
    private String name;

    /**
     * 电话号码
     */
    @NotBlank(message = "电话号码不能为空")
    private String phone;

    /**
     * 版本ID
     */
    @NotNull(message = "版本ID不能为空")
    private Long revisionId;

    /**
     * 可开通的品牌数量
     */
    @NotNull(message = "可开通品牌数量不能为空")
    @Min(value = 1, message = "可开通品牌数量不能小于1")
    private Integer brandNum;

    /**
     * 可开通的门店数量
     */
    @NotNull(message = "可开通门店数量不能为空")
    @Min(value = 1, message = "可开通门店数量不能小于1")
    private Integer storeNum;

    /**
     * 过期时间
     */
    @NotNull(message = "过期时间不能为空")
    private LocalDateTime expireTime;


}
