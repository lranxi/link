package com.lyranxi.link.web.exception;

import cn.hutool.core.util.StrUtil;
import com.lyranxi.link.common.constant.GlobalConstant;
import com.lyranxi.link.common.enums.BaseResponseCodeEnum;
import com.lyranxi.link.common.exception.BizException;
import com.lyranxi.link.common.exception.BusinessException;
import com.lyranxi.link.common.util.trace.MdcUtil;
import com.lyranxi.link.web.result.Result;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抽象异常处理器
 *
 * @author ranxi
 * @date 2025-03-12 18:51
 */
@Slf4j
public class AbstractExceptionHandler {

    private static final int OK = HttpStatus.OK.value();
    private static final String CONTENT_TYPE = MediaType.APPLICATION_JSON_VALUE;


    /**
     * 自定义的业务异常
     **/
    @ExceptionHandler(BizException.class)
    public Result<?> handleBizException(HttpServletResponse response, BizException e) {
        if (e instanceof BusinessException) {
            log.info("业务异常: {}", e.getMessage());
        } else {
            log.info("自定义异常: ", e);
        }
        setResponseStatus(response);
        return Result.from(e);
    }


    /**
     * 不支持的请求方式
     **/
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Result<?> handleHttpMediaTypeNotSupportedException(HttpServletResponse response, HttpMediaTypeNotSupportedException e) {
        log.warn("不支持的请求类型: ", e);
        setResponseStatus(response);
        return buildErrors(BaseResponseCodeEnum.NOT_SUPPORTED_MEDIA_TYPE);
    }

    /**
     * 请求数据无法读取，参数格式错误
     */
    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public Result<?> handHttpMessageNotReadableException(
            HttpServletResponse response,
            org.springframework.http.converter.HttpMessageNotReadableException e) {
        log.warn("参数格式错误:{}", e.getMessage());
        setResponseStatus(response);
        return buildErrors(BaseResponseCodeEnum.PARAM_FORMAT_ERROR);
    }

    /**
     * 参数格式错误
     **/
    @ExceptionHandler(org.springframework.web.bind.MissingServletRequestParameterException.class)
    public Result<?> handMissingServletRequestParameterException(
            HttpServletResponse response,
            org.springframework.web.bind.MissingServletRequestParameterException e) {
        setResponseStatus(response);
        Result<?> result = buildErrors(BaseResponseCodeEnum.PARAM_FORMAT_ERROR);
        result.setErrors(new HashMap<>(16));
        result.getErrors().put(e.getParameterName(), e.getMessage());
        return result;
    }

    /**
     * 参数不合法
     **/
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<?> handIllegalArgumentException(
            HttpServletResponse response,
            IllegalArgumentException e) {
        setResponseStatus(response);
        log.warn("参数不合法：{}", e.getMessage());
        return buildErrors(BaseResponseCodeEnum.PARAM_FORMAT_ERROR);
    }

    /**
     * spring mvc validation校验异常
     */
    @ExceptionHandler(BindException.class)
    public Result<?> handleBindException(
            HttpServletResponse response,
            BindException e) {
        setResponseStatus(response);
        return getResultByBindResult(e.getBindingResult());
    }

    /**
     * spring-web 参数校验，与BindException类似
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleMethodArgumentNotValidException(
            HttpServletResponse response,
            MethodArgumentNotValidException e) {
        log.error("参数校验失败: {}", e.getMessage());
        setResponseStatus(response);
        return getResultByBindResult(e.getBindingResult());
    }

    /**
     * 此种异常属于不正常异常，开发需要在代码中处理此种情况
     **/
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(
            HttpServletResponse response,
            Exception e) {
        log.error("{}: ", GlobalConstant.IMPORTANT_SYMBOL, e);
        setResponseStatus(response);
        return Result.from(BaseResponseCodeEnum.UNKNOWN);
    }


    public Result<?> getResultByBindResult(BindingResult bindingResult) {
        Result<?> result = Result.from(BaseResponseCodeEnum.PARAM_FORMAT_ERROR);
        if (bindingResult != null && bindingResult.hasErrors()) {
            List<FieldError> errorsList = bindingResult.getFieldErrors();
            if (errorsList.isEmpty()) {
                return result;
            }

            Map<String, Object> errors = new HashMap<>(errorsList.size());
            for (FieldError fieldError : errorsList) {
                String key = fieldError.getField();
                Object val = errors.get(key);
                if (val != null) {
                    val = val + "," + fieldError.getDefaultMessage();
                } else {
                    val = fieldError.getDefaultMessage();
                }
                errors.put(key, val);
            }
            result.setErrors(errors);
        }
        return result;
    }


    protected void setResponseStatus(HttpServletResponse response) {
        response.setStatus(OK);
        response.setHeader(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE);
    }


    protected Result<?> buildErrors(BaseResponseCodeEnum codeEnum, String errorMessage) {
        if (StrUtil.isNotBlank(errorMessage)) {
            if (codeEnum == BaseResponseCodeEnum.SERVER_INTERNAL_ERROR || codeEnum == BaseResponseCodeEnum.UNKNOWN) {
                errorMessage = errorMessage + "(" + MdcUtil.getTraceId() + ")";
            }
            return Result.from(codeEnum.getCode(), errorMessage + "(" + MdcUtil.getTraceId() + ")");
        }
        return Result.from(codeEnum);
    }


    protected Result<?> buildErrors(BaseResponseCodeEnum codeEnum) {
        if (codeEnum == BaseResponseCodeEnum.SERVER_INTERNAL_ERROR || codeEnum == BaseResponseCodeEnum.UNKNOWN) {
            return Result.from(codeEnum.getCode(), codeEnum.getMessage() + "(" + MdcUtil.getTraceId() + ")");
        }
        return Result.from(codeEnum);
    }

}
