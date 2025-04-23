package com.lyranxi.link.common.constant;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 
 * 全局常量定义
 **/
public class GlobalConstant {
    /**
     * 配置文件根前缀
     */
    public static final String ROOT_PREFIX = "com.lyranxi";
    /** 重要标志/重大标志 */
    public static final String IMPORTANT_SYMBOL = "important_symbol";
    /** 静态签名 */
    public static final String SIGN_STATIC = "H881c38BC280GEw2Eevc";
    
    /** 编码 */
    public static final String UTF8 = "UTF-8";
    public static final String DEFAULT_CHARSET = UTF8;
    public static final Charset DEFAULT_CHARSET_UTF8 = StandardCharsets.UTF_8;
    
    public static final String AUTHORIZATION = "Authorization";
    public static final String AUTHORIZATION_BEARER = "Bearer ";
    
    public static final String PAGE_NO = "pageNum";
    public static final String PAGE_SIZE = "pageSize";
    public static final String MAX_ID = "maxId";
    public static final String MAX_UPDATE_TIME = "maxUpdateTime";

    /** 密钥 */
    public static final String X_SECRET_KEY = "x-secret-key";

    /** 请求头 结束 */
    
    /**
     * The constant FILE_MAX_SIZE.
     */
    public static final long FILE_MAX_SIZE = 5 * 1024 * 1024;
    public static final String UNKNOWN = "unknown";

    public static final String X_FORWARDED_FOR = "X-Forwarded-For";
    public static final String X_REAL_IP = "X-Real-IP";
    public static final String PROXY_CLIENT_IP = "Proxy-Client-IP";
    public static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
    public static final String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
    public static final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";

    public static final String X_TENANT_ID = "x-tenant-id";
    public static final String X_STORE_ID = "x-store-id";
    public static final String X_CLIENT_ID = "x-client-id";
    public static final String X_DEVICE_ID = "x-device-id";
    public static final String X_TIMESTAMP = "x-timestamp";
    public static final String X_SIGN = "x-sign";

    public static final String LOCALHOST_IP = "127.0.0.1";
    public static final String LOCALHOST_IP_16 = "0:0:0:0:0:0:0:1";
    public static final int MAX_IP_LENGTH = 15;
    
    /**
     * 重定向参数标识
     */
    public static final String REDIRECT_URI = "redirect_uri";

    /** 运营后台租户ID */
    public static final String LINK_TENANT_ID = "";

    /** 默认用户头像 */
    public static final String DEFAULT_USER_AVATAR = "";
}
