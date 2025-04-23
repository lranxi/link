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
 * 用户信息
 * @TableName user
 */
@TableName(value ="`user`")
@Data
public class User implements Serializable {
    /**
     * 全局唯一ID
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
     * 登录账号
     */
    @TableField(value = "username")
    private String username;

    /**
     * 昵称
     */
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 电话号码
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * PBKDF2加密后的哈希值
     */
    @TableField(value = "password_hash")
    private String passwordHash;

    /**
     * 加密盐值
     */
    @TableField(value = "salt")
    private String salt;

    /**
     * 账户状态(1: 正常 0: 禁用)
     */
    @TableField(value = "`enable`")
    private Boolean enable;

    /**
     * 是否为系统管理员
     */
    @TableField(value = "`system`")
    private Boolean system;

    /**
     * 最后登录时间
     */
    @TableField(value = "last_login_time")
    private LocalDateTime lastLoginTime;

    /**
     * 连续失败次数
     */
    @TableField(value = "failed_attempts")
    private Integer failedAttempts;

    /**
     * 是否删除(1: 已删除/null: 未删除)
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

    /**
     * 最后修改人ID
     */
    @TableField(value = "update_by")
    private Long updateBy;

    /**
     * 最后修改时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    @Serial
    private static final long serialVersionUID = 1L;


}