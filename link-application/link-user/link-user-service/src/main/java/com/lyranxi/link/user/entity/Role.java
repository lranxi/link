package com.lyranxi.link.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色信息表
 *
 * @TableName role
 */
@TableName(value = "role")
@Data
public class Role implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id")
    private String tenantId;

    /**
     * 品牌ID
     */
    @TableField(value = "brand_id")
    private String brandId;

    /**
     * 门店ID
     */
    @TableField(value = "store_id")
    private String storeId;

    /**
     * 角色名称
     */
    @TableField(value = "role_name")
    private String roleName;

    /**
     * 角色权限范围 {@link com.lyranxi.link.user.enums.RoleScopeEnum}
     */
    @TableField(value = "role_scope")
    private Integer roleScope;

    /**
     * 是否系统角色
     */
    @TableField(value = "system")
    private Boolean system;

    /**
     * 状态(1:启用/0:停用)
     */
    @TableField(value = "enable")
    private Boolean enable;

    /**
     * 是否删除(0:未删除/null: 已删除)
     */
    @TableField(value = "deleted")
    private Integer deleted;

    /**
     * 创建人ID
     */
    @TableField(value = "create_by")
    private Long createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @TableField(value = "update_by")
    private Long updateBy;

    @TableField(value = "update_time")
    public LocalDateTime updateTime;

    @Serial
    private static final long serialVersionUID = 1L;


}