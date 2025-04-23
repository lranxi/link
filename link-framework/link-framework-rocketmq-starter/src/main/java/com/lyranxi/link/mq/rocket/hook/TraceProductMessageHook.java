package com.lyranxi.link.mq.rocket.hook;

import cn.hutool.core.util.StrUtil;
import com.lyranxi.link.common.constant.TraceConstant;
import com.lyranxi.link.common.util.trace.MdcUtil;
import org.apache.rocketmq.client.hook.SendMessageContext;
import org.apache.rocketmq.client.hook.SendMessageHook;
import org.apache.rocketmq.common.message.Message;
import org.springframework.stereotype.Component;

/**
 * @author ranxi
 * @date 2025-04-18 15:33
 */
@Component
public class TraceProductMessageHook implements SendMessageHook {

    @Override
    public String hookName() {
        return "PrintTraceProductMessageHook";
    }

    @Override
    public void sendMessageBefore(SendMessageContext sendMessageContext) {
        Message message = sendMessageContext.getMessage();
        String traceId = MdcUtil.getTraceId();
        if (StrUtil.isNotBlank(traceId)) {
            message.putUserProperty(TraceConstant.TRACE_ID, traceId);
        }
    }


    @Override
    public void sendMessageAfter(SendMessageContext sendMessageContext) {

    }
}
