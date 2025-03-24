package com.lyranxi.link.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 权限表
 * @TableName permission
 */
@TableName(value ="api_info")
@Data
public class ApiInfo implements Serializable {
    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 权限ID
     */
    private String permissionId;

    /**
     * api名称
     */
    private String apiName;

    /**
     * api路径
     */
    private String apiPath;

    /**
     * 请求方式
     */
    private Integer apiMethod;

    /** 是否白名单(白名单接口不校验权限) */
    private Boolean whitelist;

    /** 适用范围(1:运营后台/2: 代理商后台/3: 租户后台/4: 商家app/5: 用户小程序) */
    private Integer scope;

    /**
     * 创建用户
     */
    private String author;

    /**
     * 创建时间
     */
    private Date createdTime;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}