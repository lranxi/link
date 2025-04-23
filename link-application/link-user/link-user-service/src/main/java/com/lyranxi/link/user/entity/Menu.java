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
 * 菜单信息
 *
 * @TableName menu
 */
@TableName(value = "menu")
@Data
public class Menu implements Serializable {
    /**
     * 菜单ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父级ID（一级菜单pid=0）
     */
    @TableField(value = "pid")
    private Long pid;

    /**
     * 名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 路径
     */
    @TableField(value = "url")
    private String url;

    /**
     * icon
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 类型(1:目录/2:菜单/3:按钮)
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 是否有效(0:无效/1:有效)
     */
    @TableField(value = "enable")
    private Integer enable;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 平台(1:运营后台/2: 代理商后台/3: 租户后台/4: 商家app/5: 用户小程序)
     */
    @TableField(value = "platform")
    private Integer platform;

    /**
     * 是否可见
     */
    @TableField(value = "show")
    private Boolean show;

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
     * 修改时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    /**
     * 修改用户ID
     */
    @TableField(value = "update_by")
    private Long updateBy;

    @Serial
    private static final long serialVersionUID = 1L;


}