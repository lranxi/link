package com.lyranxi.link.gateway.filter;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.lyranxi.link.common.constant.TraceConstant;
import com.lyranxi.link.gateway.util.HttpUtils;
import com.lyranxi.link.gateway.util.MdcUtil;
import com.lyranxi.link.gateway.util.ServerWebExchangeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;

/**
 * @author ranxi
 * @date 2025-04-16 15:25
 */
@Slf4j
@Component
public class TraceLogFilter implements GlobalFilter, Ordered {

    public TraceLogFilter() {
        log.info("load global filter. ordered: {}", 1);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final long beginTime = System.currentTimeMillis();
        HashMap<String, String> header = new HashMap<>(16);
        // 获取traceId
        final String traceId = ServerWebExchangeUtil.getTraceIdIfNullNew(exchange.getRequest());
        header.put(TraceConstant.X_TRACE_ID, traceId);
        // 获取当前uri
        String url = exchange.getRequest().getURI().getPath();
        // 将需要访问的服务名作为p-span
        String pSpan4ServerName = getFirstPart(url);
        header.put(TraceConstant.P_SPAN, pSpan4ServerName);
        if (StringUtils.isNotBlank(pSpan4ServerName)) {
            url = url.substring(pSpan4ServerName.length() + 1);
        }
        // 将客户端ip作为c-span
        String cSpan4ClientIp = HttpUtils.getIpAddress(exchange.getRequest());
        header.put(TraceConstant.C_SPAN, cSpan4ClientIp);
        String finalUrl = url;
        // 将trace-id，p-span，c-span写入请求头往后传递
        ServerWebExchange customEx = ServerWebExchangeUtil.addHeader(exchange, header);
        Mono<Void> mono = chain.filter(customEx).then(Mono.fromRunnable(() -> {
            MdcUtil.removeAll();
            // MDC中设置trace-id，p-span，c-span，url等信息
            MdcUtil.putAll(traceId, pSpan4ServerName, cSpan4ClientIp, finalUrl);
            long consumingTime = System.currentTimeMillis() - beginTime;
            // 打印接口耗时
            log.info(consumingTime + " ms");
        }));
        // 将trace-id写入响应头中
        customEx.getResponse().getHeaders().add(MdcUtil.TRACE_ID, traceId);
        return mono;
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private String getFirstPart(String url) {
        String[] arr = url.split("/");
        return arr.length > 1 ? arr[1] : "";
    }

}
