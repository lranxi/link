package com.lyranxi.link.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * 菜单api关联信息
 * @TableName menu_api_mapping
 */
@TableName(value ="menu_api_mapping")
@Data
public class MenuApiMapping implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * menuId
     */
    private Long menuId;

    /**
     * apiId
     */
    private Long apiId;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}