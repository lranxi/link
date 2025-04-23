package com.lyranxi.link.user.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author ranxi
 * @date 2025-04-23 11:08
 */
@Data
public class BrandCreateBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "租户ID不能为空")
    private String tenantId;

    @NotBlank(message = "品牌名称不能为空")
    @Size(min = 1,max = 100,message = "品牌名称长度不能超过100个字符")
    private String name;

}
