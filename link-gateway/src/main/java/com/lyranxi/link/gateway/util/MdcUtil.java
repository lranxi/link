package com.lyranxi.link.gateway.util;

import org.slf4j.MDC;

/**
 * MDC工具
 * @author fengxiaochun
 * @date   2021-01-18 16:54
 * @since  2200
 **/
public class MdcUtil {
    /** 追踪连 */
    public final static String TRACE_ID = "trace-id";
    public final static String URL = "URL";
    /** 父span */
    public final static String P_SPAN = "p-span";
    /** 当前span */
    public final static String C_SPAN = "c-span";

    public static void putAll(String traceId, String pspan, String cspan, String uri){
        if(!isBlank(pspan)){
            MDC.put(P_SPAN, pspan);
        }
        if(!isBlank(cspan)){
            MDC.put(C_SPAN, cspan);
        }
        putTraceId(traceId);
        putUri(uri);
    }

    public static void putTraceId(String traceId){
        if(!isBlank(traceId)){
            MDC.put(TRACE_ID, traceId);
        }
    }
    public static void putUri(String uri){
        if(!isBlank(uri)){
            MDC.put(URL, uri);
        }
    }
    
    private static boolean isBlank(String s){
        return s == null || s.length() == 0;
    }

    public static void removeAll(){
        MDC.put(TRACE_ID, "");
        MDC.put(URL, "");
        MDC.put(P_SPAN, "");
        MDC.put(C_SPAN, "");
    }
}
