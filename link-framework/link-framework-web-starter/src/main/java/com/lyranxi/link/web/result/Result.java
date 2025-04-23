package com.lyranxi.link.web.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lyranxi.link.common.enums.BaseResponseCodeEnum;
import com.lyranxi.link.common.exception.BizException;
import com.lyranxi.link.common.util.trace.MdcUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * 服务接口返回结果
 * @author ranxi
 * @date 2025-03-12 20:01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Result<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /** 响应码 */
    private int code;
    /** 错误信息 */
    private String message;
    /** 响应数据 */
    private T data;

    /** 追踪链标识 */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String traceId;
    /** 错误信息 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String,Object> errors;

    public Result() {
        this(BaseResponseCodeEnum.SUCCESS.getCode(), BaseResponseCodeEnum.SUCCESS.getMessage());
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
        this.traceId = MdcUtil.getTraceId();
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.traceId = MdcUtil.getTraceId();
    }

    public Result(int code, String message, T data, Map<String, Object> errors) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.errors = errors;
        this.traceId = MdcUtil.getTraceId();
    }

    /********************************************************
     * 快速构建
     ********************************************************/
    public static <T> Result<T> from(Integer code, String msg){
        return from(code, msg, null, null);
    }
    public static <T> Result<T> from(Integer code, String msg, T data, Map<String, Object> errors){
        return new Result<>(code, msg, data, errors);
    }

    public static <T> Result<T> ok(){
        return from(BaseResponseCodeEnum.SUCCESS.getCode(), BaseResponseCodeEnum.SUCCESS.getMessage());
    }

    public static <T> Result<T> success(T data){
        return from(BaseResponseCodeEnum.SUCCESS.getCode(), BaseResponseCodeEnum.SUCCESS.getMessage(), data, null);
    }

    public static <E> Result<List<E>> success(List<E> data){
        if(data == null){
            data = new LinkedList<>();
        }
        return from(BaseResponseCodeEnum.SUCCESS.getCode(), BaseResponseCodeEnum.SUCCESS.getMessage(), data, null);
    }
    public static <E> Result<Set<E>> success(Set<E> data){
        if(data == null){
            data = new HashSet<>();
        }
        return from(BaseResponseCodeEnum.SUCCESS.getCode(), BaseResponseCodeEnum.SUCCESS.getMessage(), data, null);
    }

    public static <K,V> Result<Map<K,V>> success(Map<K,V> data){
        if(data == null){
            data = new HashMap<>(4);
        }
        return from(BaseResponseCodeEnum.SUCCESS.getCode(), BaseResponseCodeEnum.SUCCESS.getMessage(), data, null);
    }

    public static <T> Result<T> from(BizException e){
        return from(e.getCode(), e.getMessage(), null, e.getErrors());
    }

    public static <T> Result<T> from(BaseResponseCodeEnum codeEnum){
        return from(codeEnum.getCode(), codeEnum.getMessage());
    }

    @JsonIgnore
    public boolean success(){
        return code == BaseResponseCodeEnum.SUCCESS.getCode();
    }

    @JsonIgnore
    public boolean isOk(){
        return success();
    }

    /**
     * 抛出异常
     */
    public void throwException(){
        BizException exception = new BizException(this.code, this.message);
        exception.setErrors(errors);
        throw exception;
    }

    /**
     * 断言成功
     * @return Result<T>
     */
    public Result<T> assertSucceed() {
        if (!success()) {
            throwException();
        }
        return this;
    }

    /**
     * 断言成功
     * @param errorMessage 失败的提示信息
     * @return Result<T>
     */
    public Result<T> assertSucceed(String errorMessage) {
        if (!success()){
            BizException exception = new BizException(this.code, errorMessage);
            exception.setErrors(this.errors);
            throw exception;
        }
        return this;
    }

    /**
     * 返回Optional包装的data
     * @return Optional<T>
     */
    @JsonIgnore
    public Optional<T> getDataOptional() {
        return Optional.ofNullable(this.data);
    }
}
