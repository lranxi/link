package com.lyranxi.link.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 租户信息
 * @TableName tenant
 */
@TableName(value ="tenant")
@Data
public class Tenant implements Serializable {
    /**
     * 租户ID
     */
    @TableId(value = "id")
    private String id;

    /**
     * 租户名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 电话号码
     */
    @TableField(value = "phone")
    private String phone;

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
    private LocalDateTime createTime;

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