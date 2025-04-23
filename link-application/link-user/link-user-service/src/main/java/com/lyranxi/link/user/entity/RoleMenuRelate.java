package com.lyranxi.link.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * 角色菜单关联关系
 * @TableName role_menu_relate
 */
@TableName(value ="role_menu_relate")
@Data
@Builder
public class RoleMenuRelate implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色ID
     */
    @TableField(value = "role_id")
    private Long roleId;

    /**
     * 菜单ID
     */
    @TableField(value = "menu_id")
    private Long menuId;

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

    @Serial
    private static final long serialVersionUID = 1L;


}