package com.lyranxi.link.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 *
 * @TableName user
 */
@TableName(value = "user")
@Data
public class User implements Serializable {
    /**
     * 全局唯一ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 对外展示的uid
     */
    private String uid;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 门店ID
     */
    private Long storeId;


    /**
     * 带校验规则的登录账号
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * PBKDF2加密后的哈希值
     */
    private String passwordHash;

    /**
     * 加密盐值
     */
    private String salt;

    /**
     * 账户状态(1: 正常 2: 禁用 3: 锁定)
     */
    private Integer status;

    /**
     * 是否为系统管理员(1:系统用户/0:租户用户)
     */
    private Boolean system;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 连续失败次数
     */
    private Integer failedAttempts;

    /**
     * 是否删除
     */
    private Boolean deleted;

    /**
     * 创建人ID
     */
    private Long createdBy;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 最后修改人ID
     */
    private Long updatedBy;

    /**
     * 最后修改时间
     */
    private Date updatedTime;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}