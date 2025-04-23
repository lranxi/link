package com.lyranxi.link.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 品牌信息
 * @TableName brand
 */
@TableName(value ="brand")
@Data
public class Brand implements Serializable {
    /**
     * 租户ID
     */
    @TableId(value = "id")
    private String id;

    /**
     * 品牌名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id")
    private String tenantId;

    /**
     * 是否启用(1:启动/0:停用)
     */
    @TableField(value = "enable")
    private Integer enable;

    /**
     * 是否删除(0: 未删除/null: 已经删除)
     */
    @TableField(value = "deleted")
    private Integer deleted;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 创建用户ID
     */
    @TableField(value = "create_by")
    private Long createBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    /**
     * 更新用户ID
     */
    @TableField(value = "update_by")
    private Long updateBy;

    @Serial
    private static final long serialVersionUID = 1L;


}