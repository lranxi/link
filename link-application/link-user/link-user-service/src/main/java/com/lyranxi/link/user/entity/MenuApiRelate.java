package com.lyranxi.link.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 菜单api关联信息
 * @TableName menu_api_relate
 */
@TableName(value ="menu_api_relate")
@Data
public class MenuApiRelate implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 菜单ID
     */
    @TableField(value = "menu_id")
    private Long menuId;

    /**
     * 接口ID
     */
    @TableField(value = "api_id")
    private Long apiId;

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