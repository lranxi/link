package com.lyranxi.link.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 门店信息
 * @TableName store
 */
@TableName(value ="store")
@Data
public class Store implements Serializable {
    /**
     * 租户ID
     */
    @TableId(value = "id")
    private String id;

    /**
     * 门店名称
     */
    @TableField(value = "name")
    private String name;

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
     * 门店logo
     */
    @TableField(value = "logo")
    private String logo;

    /**
     * 经度
     */
    @TableField(value = "lat")
    private BigDecimal lat;

    /**
     * 纬度
     */
    @TableField(value = "lon")
    private BigDecimal lon;

    /**
     * 省份
     */
    @TableField(value = "province")
    private Integer province;

    /**
     * 城市
     */
    @TableField(value = "city")
    private Integer city;

    /**
     * 区域
     */
    @TableField(value = "region")
    private Integer region;

    /**
     * 详细地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 是否启用(1:启动/0:停用)
     */
    @TableField(value = "enable")
    private Integer enable;

    /**
     * 是否删除(0: 未删除/null: 已经删除)
     */
    @TableField(value = "deleted")
    private Integer deleted;

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
     * 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    /**
     * 更新用户ID
     */
    @TableField(value = "update_by")
    private Long updateBy;

    @Serial
    private static final long serialVersionUID = 1L;


}