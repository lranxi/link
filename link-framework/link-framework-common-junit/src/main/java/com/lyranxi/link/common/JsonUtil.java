package com.lyranxi.link.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyranxi.link.common.enums.BaseResponseCodeEnum;
import com.lyranxi.link.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * Json序列化和反序列化工具
 * @author  fengxiaochun
 * @date    2020-04-26 11:07
 **/
@Slf4j
public class JsonUtil {
    private static final char START_PREFIX = '[';
    private static ObjectMapper mapper = null;

    public static ObjectMapper getObjectMapper() {
        if (mapper == null) {
            init();
        }
        return mapper;
    }

    private static synchronized void init(){
        if(mapper == null){
            mapper = ObjectMapperUtil.getObjectMapper();
        }
    }

    /**
     * 对象转JSON字符串，会抛出"JSON序列化异常"
     *
     * @param obj
     * @return
     */
    public static String toJsonStr(Object obj) {
        try {
            return getObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("JSON序列化异常. obj: {}", obj, e);
            throw new BizException(BaseResponseCodeEnum.JSON_SERIALIZE_EXCEPTION.getCode(),
                    BaseResponseCodeEnum.JSON_SERIALIZE_EXCEPTION.getMessage());
        }
    }

    /**
     * 对象转JSON格式化字符串(美化)
     *
     * @param obj
     * @return
     */
    public static String toPrettyJsonStr(Object obj) {
        try {
            return getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("JSON序列化异常.obj: {}", obj, e);
            throw new BizException(BaseResponseCodeEnum.JSON_SERIALIZE_EXCEPTION.getCode(),
                    BaseResponseCodeEnum.JSON_SERIALIZE_EXCEPTION.getMessage());
        }
    }

    /**
     * JSON字符串转对象,会抛出"JSON解析异常"
     *
     * @param jsonStr json格式字符串
     * @param clz     对象的class
     */
    public static <T> T toObject(String jsonStr, Class<T> clz) {
        try {
            return getObjectMapper().readValue(jsonStr, clz);
        } catch (IOException e) {
            log.error("JSON解析异常, jsonStr: {}, clz: {}", jsonStr, clz, e);
            throw new BizException(BaseResponseCodeEnum.JSON_PARSE_EXCEPTION.getCode(),
                    BaseResponseCodeEnum.JSON_PARSE_EXCEPTION.getMessage());
        }
    }

    /**
     * JSON字符串转对象,会抛出"JSON解析异常"

     * @param jsonStr       json格式字符串
     * @param typeReference 类型引用(有范型的目标)
     */
    public static <T> T toObject(String jsonStr, TypeReference<T> typeReference) {
        try {
            return getObjectMapper().readValue(jsonStr, typeReference);
        } catch (IOException e) {
            log.error("JSON解析异常, jsonStr: {}, typeReference: {}", jsonStr, typeReference, e);
            throw new BizException(BaseResponseCodeEnum.JSON_PARSE_EXCEPTION.getCode(),
                    BaseResponseCodeEnum.JSON_PARSE_EXCEPTION.getMessage());
        }
    }

    /**
     * JSON字符串转数组对象
     *
     * @param jsonStr json格式字符串
     * @param clz     数组内容对象的class
     */
    public static <T> List<T> toArrayObject(String jsonStr, Class<T> clz) {
        if (StringUtils.hasLength(jsonStr)) {
            return new LinkedList<>();
        }
        if (jsonStr.charAt(0) != START_PREFIX) {
            throw new BizException(BaseResponseCodeEnum.JSON_PARSE_EXCEPTION.getCode(),
                    BaseResponseCodeEnum.JSON_PARSE_EXCEPTION.getMessage() + "非[开头");
        }
        try {
            return getObjectMapper().readValue(jsonStr, new TypeReference<List<T>>() {});
        } catch (IOException e) {
            log.error("JSON字符串转数组对象异常, jsonStr: {}, clz: {}", jsonStr, clz, e);
            throw new BizException(BaseResponseCodeEnum.JSON_PARSE_EXCEPTION.getCode(),
                    BaseResponseCodeEnum.JSON_PARSE_EXCEPTION.getMessage());
        }
    }
}
