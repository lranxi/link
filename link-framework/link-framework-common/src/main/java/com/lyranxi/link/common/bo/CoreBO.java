package com.lyranxi.link.common.bo;

import com.lyranxi.link.common.page.PageInfo;
import com.lyranxi.link.common.util.asserts.AssertRequestParameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author ranxi
 * @date 2025-04-07 17:56
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
public class CoreBO<T> implements Serializable {

    /**
     * 请求内容
     */
    @Valid
    @NotNull
    private T content;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 租户ID
     */
    private String brandId;

    /**
     * 门店ID
     */
    private String storeId;

    /**
     * 客户端ID
     */
    private Integer clientId;

    /**
     * 操作用户ID
     */
    private Long operatorId;

    /**
     * 分页信息
     */
    private PageInfo pageInfo;


    private CoreBO() {
    }

    public static <T> CoreBO<T> fromContent(T content) {
        CoreBO<T> coreBO = new CoreBO<>();
        if (content != null) {
            coreBO.setContent(content);
        } else {
            coreBO.setContent((T) new Object());
        }
        return coreBO;
    }

    public void assertOperatorIdNonNull() {
        AssertRequestParameter.nonNull(this.operatorId, "操作用户ID不能为空");
    }


}
