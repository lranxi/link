package com.lyranxi.link.member.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 会员信息
 * @TableName member
 */
@TableName(value ="member")
@Data
public class Member implements Serializable {
    /**
     * 会员ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id")
    private String tenantId;

    /**
     * 品牌 ID
     */
    @TableField(value = "brand_id")
    private String brandId;

    /**
     * 门店ID
     */
    @TableField(value = "store_id")
    private String storeId;

    /**
     * 顾客ID
     */
    @TableField(value = "customer_id")
    private Long customerId;

    /**
     * 注册门店ID
     */
    @TableField(value = "registered_store_id")
    private String registeredStoreId;

    /**
     * 注册客户端ID
     */
    @TableField(value = "registered_client_id")
    private Integer registeredClientId;

    /**
     * 状态
     */
    @TableField(value = "enable")
    private Integer enable;

    /**
     * 是否删除
     */
    @TableField(value = "deleted")
    private Integer deleted;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 创建用户
     */
    @TableField(value = "create_by")
    private Long createBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 更新用户
     */
    @TableField(value = "update_by")
    private Long updateBy;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}