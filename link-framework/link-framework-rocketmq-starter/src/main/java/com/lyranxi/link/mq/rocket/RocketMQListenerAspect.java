package com.lyranxi.link.mq.rocket;

import cn.hutool.core.util.StrUtil;
import com.lyranxi.link.common.util.trace.MdcUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;

/**
 * @author ranxi
 * @date 2025-04-18 15:41
 */
@Slf4j
@Order(1)
@Aspect
public class RocketMQListenerAspect {

    public RocketMQListenerAspect() {
    }


    @Before(value = "execution(public void org.apache.rocketmq.spring.core.RocketMQListener.onMessage())")
    public void before(JoinPoint point) {
        if (StrUtil.isBlank(MdcUtil.getTraceId())) {
            MdcUtil.setTraceId();
        }
    }

}
