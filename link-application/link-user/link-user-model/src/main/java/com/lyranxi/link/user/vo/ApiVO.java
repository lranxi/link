package com.lyranxi.link.user.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author ranxi
 * @date 2025-04-15 17:08
 */
@Data
public class ApiVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 接口code码
     */
    private String code;
    /**
     * 接口请求方式
     */
    private String method;
    /**
     * 接口路径
     */
    private String url;
    /**
     * 适用范围 {@link com.lyranxi.link.common.enums.PlatformEnum}
     */
    private Integer scope;
    /**
     * 是否白名单,白名单不验证token，0非1是，默认0
     */
    private Boolean whitelist;
    /**
     * 是否有效，1有效0无效默认1
     */
    private Boolean valid;

    /** 作者信息 */
    private String author;

}
