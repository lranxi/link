package com.lyranxi.link.user.mq.consumer;

import cn.hutool.core.util.StrUtil;
import com.lyranxi.link.common.constant.mq.GroupConstant;
import com.lyranxi.link.common.constant.mq.TopicConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author ranxi
 * @date 2025-04-22 11:41
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = TopicConstant.USER,
        consumerGroup = GroupConstant.USER + StrUtil.DASHED + TopicConstant.USER + "%t_sms_send_result",
        selectorExpression = "t_sms_send_result"
)
public class SmsSendResultConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        log.info("s");
    }
}
