package com.lyranxi.link.redis.unrepatable;

import cn.hutool.core.util.StrUtil;
import com.lyranxi.link.common.enums.BaseResponseCodeEnum;
import com.lyranxi.link.redis.exception.LinkCacheException;
import com.lyranxi.link.redis.lock.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 不可重复请求处理切面
 *
 * @author ranxi
 * @date 2025-03-04 15:29
 */
@Slf4j
@Order(1)
@Aspect
public class UnrepeatableAspect {

    private static final SpelExpressionParser PARSER = new SpelExpressionParser();
    private static final StandardReflectionParameterNameDiscoverer NAME_DISCOVERER = new StandardReflectionParameterNameDiscoverer();

    @Around("@annotation(unrepeatable)")
    public Object around(ProceedingJoinPoint joinPoint, Unrepeatable unrepeatable) throws Throwable {
        String[] array = this.parseSpEL(joinPoint, new String[]{unrepeatable.key(), unrepeatable.remark()});
        String key = array[0];
        if (StrUtil.isBlank(key)) {
            throw new IllegalArgumentException("unrepeatable key is blank");
        }
        if (StrUtil.isNotBlank(unrepeatable.prefix())) {
            key = unrepeatable.prefix() + key;
        }
        String remark = array[1];
        RedisLock lock = new RedisLock(key, unrepeatable.leaseTime(), remark);
        if (lock.tryLock(unrepeatable.waitTime())) {
            if (log.isDebugEnabled()) {
                log.debug("unrepeatable lock hold");
            }
            try {
                return joinPoint.proceed();
            } finally {
                if (unrepeatable.autoUnlock()) {
                    try {
                        lock.unlock();
                    } catch (Exception ignore) {
                    }
                }
                if (log.isDebugEnabled()) {
                    log.debug("unrepeatable lease lock");
                }
            }
        } else {
            log.warn("{} repetitive.", key);
            remark = lock.getRemark();
            if (StrUtil.isNotBlank(remark)) {
                throw new LinkCacheException(BaseResponseCodeEnum.CACHE_REPETITIVE.getCode(), remark);
            } else {
                throw new LinkCacheException(BaseResponseCodeEnum.CACHE_REPETITIVE);
            }
        }
    }

    /**
     * 解析spEL表达式
     *
     * @param joinPoint  切入点
     * @param spELArrays spEL数组
     * @return String[] 解析后的数组
     */
    private String[] parseSpEL(ProceedingJoinPoint joinPoint, String[] spELArrays) {
        Object[] args = joinPoint.getArgs();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String[] params = NAME_DISCOVERER.getParameterNames(method);
        StandardEvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < Objects.requireNonNull(params).length; i++) {
            context.setVariable(params[i], args[i]);
        }
        String[] array = new String[spELArrays.length];
        for (int i = 0; i < spELArrays.length; i++) {
            String spEL = spELArrays[i];
            if (StrUtil.isBlank(spEL) || !isSpEL(spEL)) {
                if (log.isDebugEnabled()) {
                    log.debug("spEL is blank or not spEL. {}", spEL);
                }
                array[i] = spEL;
                continue;
            }
            try {
                Expression expression = PARSER.parseExpression(spEL);
                array[i] = expression.getValue(context, String.class);
            } catch (Exception e) {
                array[i] = spEL;
                if (log.isWarnEnabled()) {
                    log.warn("解析spEL失败. spEL: {}", spEL, e);
                }
            }
        }
        return array;
    }


    private boolean isSpEL(String str) {
        return StrUtil.contains(str, "#");
    }

}
