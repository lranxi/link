package com.lyranxi.link.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ranxi
 * @date 2025-05-06 16:03
 */
@Data
@ConfigurationProperties(prefix = "weixin.miniapp")
public class WeixinMiniAppProperties {

    /** 小程序appId */
    private String appId;

    /** 小程序secret */
    private String secret;

    /** 消息服务器 token */
    private String  token;

    /** 消息服务器ase key */
    private String aesKey;

    /** 消息格式 XML / JSON */
    private String msgDataFormat;
}
