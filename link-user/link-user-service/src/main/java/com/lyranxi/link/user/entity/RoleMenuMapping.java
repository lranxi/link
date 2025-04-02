package com.lyranxi.link.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName role_menu_mapping
 */
@TableName(value ="role_menu_mapping")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenuMapping implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 菜单ID
     */
    private String menuId;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}