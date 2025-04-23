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
 * 租户版本关联关系
 * @TableName tenant_revision_relate
 */
@TableName(value ="tenant_revision_relate")
@Data
public class TenantRevisionRelate implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id")
    private String tenantId;

    /**
     * 版本ID
     */
    @TableField(value = "revision_id")
    private Long revisionId;

    /**
     * 使用开始时间
     */
    @TableField(value = "begin_time")
    private LocalDateTime beginTime;

    /**
     * 使用结束时间
     */
    @TableField(value = "end_time")
    private LocalDateTime endTime;

    /**
     * 可开通的品牌数据数量
     */
    @TableField(value = "brand_num")
    private Integer brandNum;

    /**
     * 可开通的门店数量
     */
    @TableField(value = "store_num")
    private Integer storeNum;

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
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}