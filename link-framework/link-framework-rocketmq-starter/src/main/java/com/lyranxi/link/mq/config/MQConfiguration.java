package com.lyranxi.link.mq.config;

import com.lyranxi.link.mq.rocket.RocketMQHookRegister;
import com.lyranxi.link.mq.rocket.RocketMQListenerAspect;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.DefaultRocketMQListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;

/**
 * @author ranxi
 * @date 2025-04-18 15:43
 */
@Slf4j
@Configuration
@Import(RocketMQAutoConfiguration.class)
public class MQConfiguration {

    @Bean(initMethod = "init")
    public RocketMQHookRegister rocketMQHookRegister(List<RocketMQTemplate> templates,
                                                     List<DefaultRocketMQListenerContainer> containers) {
        log.info("registry RocketMQHookRegister");
        return new RocketMQHookRegister(templates, containers);
    }

    @Bean
    public RocketMQListenerAspect orderProduceBean() {
        log.info("registry RocketMQListenerAspect");
        return new RocketMQListenerAspect();
    }


}
