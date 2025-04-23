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
 * 接口信息
 * @TableName api
 */
@TableName(value ="api")
@Data
public class Api implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * api名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * api路径
     */
    @TableField(value = "url")
    private String url;

    /**
     * 请求方式(1: GET/2: POST/3: PUT/4: DELETE)
     */
    @TableField(value = "method")
    private String method;

    /**
     * 是否白名单(白名单接口不校验权限)
     */
    @TableField(value = "whitelist")
    private Boolean whitelist;

    /**
     * 适用范围(1:运营后台/2: 代理商后台/3: 租户后台/4: 商家app/5: 用户小程序)
     */
    @TableField(value = "scope")
    private Integer scope;

    /**
     * 作者
     */
    @TableField(value = "author")
    private String author;

    /** 是否有效 */
    @TableField(value = "valid")
    private Boolean valid;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @Serial
    private static final long serialVersionUID = 1L;


}