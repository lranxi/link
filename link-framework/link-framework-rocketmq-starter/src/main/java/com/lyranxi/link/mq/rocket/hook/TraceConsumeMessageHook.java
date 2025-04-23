package com.lyranxi.link.mq.rocket.hook;

import cn.hutool.core.util.StrUtil;
import com.lyranxi.link.common.constant.TraceConstant;
import com.lyranxi.link.common.util.trace.MdcUtil;
import org.apache.rocketmq.client.hook.ConsumeMessageContext;
import org.apache.rocketmq.client.hook.ConsumeMessageHook;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * MQ消息消费钩子
 *
 * @author ranxi
 * @date 2025-04-18 15:28
 */
@Component
public class TraceConsumeMessageHook implements ConsumeMessageHook {

    @Override
    public String hookName() {
        return "PrintTraceConsumeMessageHook";
    }

    @Override
    public void consumeMessageBefore(ConsumeMessageContext consumeMessageContext) {
        List<MessageExt> msgList = consumeMessageContext.getMsgList();
        if (!CollectionUtils.isEmpty(msgList)) {
            for (MessageExt messageExt : msgList) {
                String traceId = messageExt.getUserProperty(TraceConstant.TRACE_ID);
                if (StrUtil.isNotBlank(traceId)) {
                    MdcUtil.setTraceId(traceId);
                }
            }
        }
    }

    @Override
    public void consumeMessageAfter(ConsumeMessageContext consumeMessageContext) {

    }
}
