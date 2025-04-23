package com.lyranxi.link.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 基础业务响应码
 *
 * @author ranxi
 * @date 2025-02-28 12:20
 */
@Getter
@RequiredArgsConstructor
public enum BaseResponseCodeEnum {

    SUCCESS(0, "成功"),

    PARAM_FORMAT_ERROR(500000, "参数格式错误"),
    PARAM_NON_NULL(500001, "参数不能为空"),
    PARAM_PLATFORM_ERROR(500002, "不支持的平台"),
    PARAM_USER_TYPE_ERROR(500003, "不支持的用户类型"),


    /**
     * 业务异常
     */
    BUSINESS_EXCEPTION(600000, ""),


    NOT_SUPPORTED_MEDIA_TYPE(999702, "不支持的请求类型"),
    HTTP_ERROR(999703, "请求第三方资源异常"),

    SERVER_INTERNAL_ERROR(999801, "服务内部错误(请联系技术支持)"),
    JSON_SERIALIZE_EXCEPTION(999802, "JSON序列化异常"),
    JSON_PARSE_EXCEPTION(999802, "JSON解析异常"),

    CACHE_ERROR(999901, "缓存异常"),
    CACHE_REPETITIVE(999902, "重复请求"),
    UNKNOWN(999999, "未知错误(请联系技术支持)");

    private final int code;
    private final String message;


}
