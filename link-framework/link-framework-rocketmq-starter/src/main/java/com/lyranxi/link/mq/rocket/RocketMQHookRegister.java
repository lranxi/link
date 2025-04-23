package com.lyranxi.link.mq.rocket;

import cn.hutool.core.collection.CollectionUtil;
import com.lyranxi.link.mq.rocket.hook.TraceConsumeMessageHook;
import com.lyranxi.link.mq.rocket.hook.TraceProductMessageHook;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.DefaultRocketMQListenerContainer;

import java.util.List;

/**
 * @author ranxi
 * @date 2025-04-18 15:35
 */
@Slf4j
public class RocketMQHookRegister {

    private final List<RocketMQTemplate> rocketMQTemplates;
    private final List<DefaultRocketMQListenerContainer> defaultRocketMQListenerContainer;

    public RocketMQHookRegister(List<RocketMQTemplate> rocketMQTemplates, List<DefaultRocketMQListenerContainer> defaultRocketMQListenerContainer) {
        this.rocketMQTemplates = rocketMQTemplates;
        this.defaultRocketMQListenerContainer = defaultRocketMQListenerContainer;
    }

    public void init() {
        log.info("starter register rocketmq trace hook");
        if (CollectionUtil.isEmpty(rocketMQTemplates)) {
            log.error("rocket mq templates is null");
            throw new RuntimeException("加载RocketMQ Template list 失败");
        }
        // 注册生产者hook
        for (RocketMQTemplate rocketMQTemplate : rocketMQTemplates) {
            if (rocketMQTemplate != null && rocketMQTemplate.getProducer() != null) {
                log.info("producer: {} register message hook", rocketMQTemplate);
                rocketMQTemplate.getProducer().getDefaultMQProducerImpl()
                        .registerSendMessageHook(new TraceProductMessageHook());
            }
        }

        if (CollectionUtil.isEmpty(defaultRocketMQListenerContainer)) {
            log.warn("defaultRocketMQListenerContainer is empty");
            return;
        }

        for (DefaultRocketMQListenerContainer rocketMQListenerContainer : defaultRocketMQListenerContainer) {
            if (rocketMQListenerContainer != null && rocketMQListenerContainer.getConsumer() != null) {
                log.info("consumer: {} register message hook", rocketMQListenerContainer);
                rocketMQListenerContainer.getConsumer().getDefaultMQPushConsumerImpl()
                        .registerConsumeMessageHook(new TraceConsumeMessageHook());
            }
        }

    }

}
