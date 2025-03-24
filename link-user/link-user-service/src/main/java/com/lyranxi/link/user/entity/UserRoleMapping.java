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
 * 用户角色映射表
 * @TableName user_role_mapping
 */
@TableName(value ="user_role_mapping")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleMapping implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 
     */
    private String userId;

    /**
     * 
     */
    private String roleId;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}