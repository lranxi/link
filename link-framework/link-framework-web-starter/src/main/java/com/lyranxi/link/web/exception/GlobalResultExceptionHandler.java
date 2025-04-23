package com.lyranxi.link.web.exception;

import com.lyranxi.link.common.enums.BaseResponseCodeEnum;
import com.lyranxi.link.web.result.Result;
import feign.FeignException;
import jakarta.annotation.Priority;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局{@code Result}异常捕获处理器
 * @author fengxiaochun
 * @date 2022/3/22 10:30
 * @since 2203
 */
@Slf4j
@ControllerAdvice
@ResponseBody
@Priority(1)
public class GlobalResultExceptionHandler extends AbstractExceptionHandler {
    private static final String FEIGN_HTTP_TIMEOUT = "Read timed out executing ";
    private static final String FEIGN_HTTP_TIMEOUT_MSG = "接口超时";

    @ExceptionHandler(FeignException.class)
    public Result<?> handleFeignException(
            HttpServletResponse response,
            FeignException e) {
        setResponseStatus(response);
        String message = e.getMessage();
        if (message != null && message.contains(FEIGN_HTTP_TIMEOUT)) {
            log.error("Feign异常: {}", message);
            // 内部调用feign超时
            return Result.from(BaseResponseCodeEnum.HTTP_ERROR.getCode(), FEIGN_HTTP_TIMEOUT_MSG);
        }
        log.error("Feign异常:", e);
        return buildErrors(BaseResponseCodeEnum.HTTP_ERROR, "内部Feign调用异常");
    }
}
