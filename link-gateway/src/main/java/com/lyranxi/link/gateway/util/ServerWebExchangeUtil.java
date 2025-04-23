package com.lyranxi.link.gateway.util;

import com.alibaba.cloud.commons.lang.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * pig-infrastructure
 *
 * @author fengxiaochun
 * @date 2021-05-25 18:27
 **/
@Slf4j
public class ServerWebExchangeUtil {
    /**
     * 请求头增加数据
     * @param exchange ServerWebExchange
     * @param header   增加的请求头
     * @return 新的ServerWebExchange
     */
    public static ServerWebExchange addHeader(ServerWebExchange exchange, Map<String, String> header) {
        if(header == null || header.isEmpty()){
            return exchange;
        }
        ServerHttpRequest.Builder mutate = exchange.getRequest().mutate();
        for (Map.Entry<String, String> entry : header.entrySet()) {
            mutate.header(entry.getKey(), new String[]{entry.getValue()});
        }
        return exchange.mutate().request(mutate.build()).build();
    }
    
    /**
     * 像请求头增减数据
     * @author fengxiaochun
     * @date   2021/12/6 11:38
     * @param  exchange  ServerWebExchange
     * @param  key       请求头key
     * @param  value     请求头值
     * @return ServerWebExchange 新的ServerWebExchange
     * @since  22--
     **/
    public static ServerWebExchange addHeader(ServerWebExchange exchange, String key, String value) {
        ServerHttpRequest request = exchange.getRequest().mutate()
                .header(key, new String[]{value})
                .build();
        return exchange.mutate().request(request).build();
    }

    /**
     * 构建返回
     * @param exchange     exchange
     * @param responseBody 相应内容
     * @return mono
     */
    public static Mono<Void> buildReturnMono(ServerWebExchange exchange, String responseBody) {
        ServerHttpResponse response = exchange.getResponse();
        DataBuffer buffer = response.bufferFactory().wrap(responseBody.getBytes(StandardCharsets.UTF_8));
        //response.setStatusCode(HttpStatus.UNAUTHORIZED)
        response.setStatusCode(HttpStatus.OK);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return response.writeWith(Mono.just(buffer));
    }

    public static String getTraceIdIfNullNew(ServerWebExchange exchange){
        return getTraceIdIfNullNew(exchange.getRequest());
    }
    
    /**
     * 从请求头中获取traceId,不存在则生成一个新的返回
     * @author fengxiaochun
     * @date   2021/12/6 11:31
     * @param  request
     * @return String
     * @since  22--
     **/
    public static String getTraceIdIfNullNew(ServerHttpRequest request){
        String traceId = request.getHeaders().getFirst(MdcUtil.TRACE_ID);
        return StringUtils.isNotBlank(traceId) ? traceId : generateTraceId();
    }
    
    /**
     * 生成traceId,有线试用分布式Id生成,生成失败在使用UUID
     * @author fengxiaochun
     * @date   2022/7/7 11:09
     * @return String
     * @since  2208
     **/
    private static String generateTraceId(){
//        try {
//            return String.valueOf(IdGeneratorUtil.generateId());
//        } catch (Exception e) {
//            if (log.isWarnEnabled()) {
//                log.error("生成traceId失败, {}", e.getMessage());
//            }
//            return Base58.uuid22();
//        }
        return Base58.uuid22();
    }
    
    /**
     * 从请求头中获取p-span标记，如果不存在则新生成
     * @author fengxiaochun
     * @date   2021/12/6 11:30
     * @param  request
     * @return String
     * @since  22--
     **/
    public static String getPSpanIfNullNew(ServerHttpRequest request) {
        String pSpan = request.getHeaders().getFirst(MdcUtil.P_SPAN);
        return StringUtils.isBlank(pSpan) ? Base58.uuid22() : pSpan;
    }
    
    /**
     * 从请求头中获取c-span标记，如果不存在则新生成
     * @author fengxiaochun
     * @date   2021/12/6 11:30
     * @param  request
     * @return String
     * @since  22--
     **/
    public static String getCSpanIfNullNew(ServerHttpRequest request) {
        String cSpan = request.getHeaders().getFirst(MdcUtil.C_SPAN);
        return StringUtils.isBlank(cSpan) ? Base58.uuid22() : cSpan;
    }
}
