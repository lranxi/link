package com.lyranxi.link.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 版本信息
 * @TableName revision
 */
@TableName(value ="revision")
@Data
public class Revision implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 版本名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 价格(年付款)
     */
    @TableField(value = "year_price")
    private BigDecimal yearPrice;

    /**
     * 价格(季度)
     */
    @TableField(value = "quarter_price")
    private BigDecimal quarterPrice;

    /**
     * 价格（月）
     */
    @TableField(value = "month_price")
    private BigDecimal monthPrice;

    /**
     * 模板角色ID
     */
    @TableField(value = "template_role_id")
    private Long templateRoleId;

    /**
     * 可开通的品牌数量
     */
    @TableField(value = "brand_num")
    private Integer brandNum;

    /**
     * 可开通的门店数量
     */
    @TableField(value = "store_num")
    private Integer storeNum;

    /**
     * 状态
     */
    @TableField(value = "enable")
    private Boolean enable;

    /**
     * 是否删除
     */
    @TableField(value = "deleted")
    private Boolean deleted;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 创建用户ID
     */
    @TableField(value = "create_by")
    private Long createBy;

    /**
     * 更新用户id
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 更新用户ID
     */
    @TableField(value = "update_by")
    private Long updateBy;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}