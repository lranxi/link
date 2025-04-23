package com.lyranxi.link.user.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ranxi
 * @date 2025-04-23 11:29
 */
@Data
public class StoreCreateBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 门店名称
     */
    @NotBlank(message = "门店名称不能为空")
    @Size(min = 1,max = 100,message = "门店名称长度不能超过100个字符")
    private String name;

    /**
     * 租户ID
     */
    @NotBlank(message = "租户ID不能为空")
    private String tenantId;

    /**
     * 品牌ID
     */
    @NotBlank(message = "品牌ID不能为空")
    private String brandId;

    /**
     * 门店logo
     */
    @Size(min = 1,max = 200,message = "门店logo图片地址长度不能超过200个字符")
    private String logo;

    /**
     * 经度
     */
    @NotNull(message = "经度不能为空")
    private BigDecimal lat;

    /**
     * 纬度
     */
    @NotNull(message = "纬度不能为空")
    private BigDecimal lon;

    /**
     * 省份
     */
    @NotNull(message = "省份不能为空")
    private Integer province;

    /**
     * 城市
     */
    @NotNull(message = "城市不能为空")
    private Integer city;

    /**
     * 区域
     */
    @NotNull(message = "区域不能为空")
    private Integer region;

    /**
     * 详细地址
     */
    @NotBlank(message = "门店详细地址不能为空")
    private String address;


}
