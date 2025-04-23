package com.lyranxi.link.user.bo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author ranxi
 * @date 2025-04-17 15:08
 */
@Data
public class ApiSearchBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    /** 接口名称,模糊匹配 */
    private String name;
    /** 接口请求方式 */
    private String method;
    /** 接口路径,模糊匹配 */
    private String url;
    /** 适用范围(1运营后台/2商家后台/3代理商后台/5收银台/6App) */
    private Integer scope;
    /** 是否白名单,白名单不验证token，0非1是，默认0 */
    private Boolean whitelist;
    /** 是否有效，1有效0无效默认1 */
    private Boolean valid;
    /** 作者,模糊匹配 */
    private String author;

}
