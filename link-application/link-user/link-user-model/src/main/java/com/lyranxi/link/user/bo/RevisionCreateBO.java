package com.lyranxi.link.user.bo;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

/**
 * @author ranxi
 * @date 2025-04-21 16:42
 */
@Data
public class RevisionCreateBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 版本名称
     */
    @NotBlank(message = "版本名称不能为空")
    private String name;

    /**
     * 每年价格
     */
    @NotNull(message = "年度价格不能为空")
    @DecimalMin(value = "0.01", message = "年度价格最小值为0")
    @DecimalMax(value = "999999.99", message = "年度价格最大值为999999.99")
    @Digits(integer = 6, fraction = 2, message = "年度价格最多两位小数")
    private BigDecimal yearPrice;

    /**
     * 每季度价格
     */
    @NotNull(message = "季度价格不能为空")
    @DecimalMin(value = "0.01", message = "季度价格最小值为0")
    @DecimalMax(value = "999999.99", message = "季度价格最大值为999999.99")
    @Digits(integer = 6, fraction = 2, message = "季度价格最多两位小数")
    private BigDecimal quarterPrice;

    /**
     * 每月价格
     */
    @NotNull(message = "月度价格不能为空")
    @DecimalMin(value = "0.01", message = "月度价格最小值为0")
    @DecimalMax(value = "999999.99", message = "月度价格最大值为999999.99")
    @Digits(integer = 6, fraction = 2, message = "月度价格最多两位小数")
    private BigDecimal monthPrice;

    /**
     * 可开通品牌数量
     */
    @NotNull(message = "可开通品牌数量不能为空")
    @NotNull(message = "可开通品牌数量最小值为1")
    private Integer brandNum;

    /**
     * 可开通门店数量
     */
    @NotNull(message = "可开通门店数量不能为空")
    @Min(value = 1, message = "可开通门店数量最小值为1")
    private Integer storeNum;

    /**
     * 菜单权限
     */
    @NotEmpty(message = "菜单权限不能为空")
    private Set<Long> menuIds;

}
