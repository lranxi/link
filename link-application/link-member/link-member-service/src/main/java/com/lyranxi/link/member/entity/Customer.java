package com.lyranxi.link.member.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 顾客信息
 * @TableName custoemr
 */
@TableName(value ="customer")
@Data
public class Customer implements Serializable {
    /**
     * 
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id")
    private String tenant_id;

    /**
     * 品牌ID
     */
    @TableField(value = "brand_id")
    private String brand_id;

    /**
     * 门店id
     */
    @TableField(value = "store_id")
    private String store_id;

    /**
     * 电话号码
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 昵称
     */
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 性别(1: 男/2: 女/3: 未知)
     */
    @TableField(value = "gender")
    private Integer gender;

    /**
     * 生日
     */
    @TableField(value = "birthday")
    private String birthday;

    /**
     * 头像url
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 微信unionId
     */
    @TableField(value = "wx_unoin_id")
    private String wx_unoin_id;

    /**
     * 微信openId
     */
    @TableField(value = "wx_open_id")
    private String wx_open_id;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date create_time;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}