package com.lyranxi.link.user.mq.producer;

import com.lyranxi.link.common.constant.mq.TopicConstant;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

/**
 * @author ranxi
 * @date 2025-04-23 13:51
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RocketMQProducer {
    private final RocketMQTemplate rocketMQTemplate;


    /**
     * 发送顺序消息
     *
     * @param tag     tag
     * @param data    data
     * @param hashKey hashKey
     * @return 发送结果
     */
    public <T> SendResult syncSendOrderly(final String tag, T data, final String hashKey) {
        return rocketMQTemplate.syncSendOrderly(
                this.buildDestination(tag),
                new GenericMessage<>(data),
                hashKey);
    }

    /**
     * 发送普通消息
     *
     * @param tag  tag
     * @param data data
     * @return 发送结果
     */
    public <T> SendResult syncSend(final String tag, T data) {
        return rocketMQTemplate.syncSend(this.buildDestination(tag), new GenericMessage<>(data));
    }

    /**
     * 异步发送普通消息
     *
     * @param tag  tag
     * @param data data
     */
    public <T> void send(final String tag, T data) {
        rocketMQTemplate.send(this.buildDestination(tag), new GenericMessage<>(data));
    }

    /**
     * 构建destination
     *
     * @param tag tag
     * @return destination
     */
    private String buildDestination(@NotBlank final String tag) {
        return TopicConstant.USER + TopicConstant.M + tag;

    }


}
