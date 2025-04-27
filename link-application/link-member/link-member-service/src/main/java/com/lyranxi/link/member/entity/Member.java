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
 * 
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
    private String tenant_id;

    /**
     * 品牌 ID
     */
    @TableField(value = "brand_id")
    private String brand_id;

    /**
     * 门店ID
     */
    @TableField(value = "store_id")
    private String store_id;

    /**
     * 顾客ID
     */
    @TableField(value = "customer_id")
    private Long customer_id;

    /**
     * 注册门店ID
     */
    @TableField(value = "registered_store_id")
    private String registered_store_id;

    /**
     * 注册客户端ID
     */
    @TableField(value = "registered_client_id")
    private Integer registered_client_id;

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
    private Date create_time;

    /**
     * 创建用户
     */
    @TableField(value = "create_by")
    private Long create_by;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date update_time;

    /**
     * 更新用户
     */
    @TableField(value = "update_by")
    private Long update_by;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}