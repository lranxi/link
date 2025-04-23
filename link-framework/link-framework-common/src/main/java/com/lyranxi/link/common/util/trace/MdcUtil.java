package com.lyranxi.link.common.util.trace;

import com.lyranxi.link.common.constant.TraceConstant;
import com.lyranxi.link.common.util.uuid.UuidUtil;
import org.slf4j.MDC;

import java.util.Map;

/**
 * traceId长度为25位长度的字符串
 * MDC操作工具
 * @author  fengxiaochun
 * @date    2020-04-26 11:47
 **/
public class MdcUtil {
    public static final String TRACE_ID = TraceConstant.TRACE_ID;
    public static final String P_SPAN = TraceConstant.P_SPAN;
    public static final String ROOT_P_SPAN = "root_span";
    public static final String C_SPAN = TraceConstant.C_SPAN;
    public static final String URL = "URL";
    public static final String COST = "cost";
    public static final String OTHER = "OTHER";
    public static final String VERSION = TraceConstant.VERSION;
    
    /********************************************************************
     * set
     ********************************************************************/
    public static void set(String key, String value) {
        MDC.put(key, value);
    }

    /**
     * 设置追踪链
     * @param traceId 追踪连
     */
    public static void setTraceId(String traceId) {
        if (isBlank(traceId)) {
            set(TRACE_ID, generateTraceId());
        } else {
            set(TRACE_ID, traceId);
        }
    }

    /**
     * 设置父链路的span
     * @param span span
     */
    public static void setPSpan(String span){
        if(isBlank(span)){
            span = ROOT_P_SPAN;
        }
        set(P_SPAN, span);
    }

    /**
     * 设置当前链路的span
     * @param span span
     */
    public static void setCSpan(String span){
        if(isBlank(span)){
            span = UuidUtil.uuid22();
        }
        set(C_SPAN, span);
    }

    /**
     * 设置追踪链
     */
    public static void setTraceId() {
        setTraceId(null);
    }

    public static void setOther(String s) {
        set(OTHER, s);
    }

    /********************************************************************
     * get
     ********************************************************************/
    public static String get(String key) {
        return MDC.get(key);
    }

    public static String getTraceId() {
        return get(TRACE_ID);
    }

    public static String getPSpan(){
        return get(P_SPAN);
    }

    public static String getCSpan(){
        return get(C_SPAN);
    }

    public static String getURL() {
        return get(URL);
    }
    public static String getVersion(){
        return get(VERSION);
    }

    private static boolean isBlank(String s){
        return s == null || s.length() == 0;
    }
    
    public static String generateTraceId(){
        return UuidUtil.uuid22();
    }

    @Deprecated
    public static Trace toTrace(){
        return new Trace(getTraceId(), getPSpan(), getCSpan());
    }

    @Deprecated
    public static void ofTrace(Trace trace){
        if (trace == null) {
            return;
        }
        setTraceId(trace.traceId);
        setPSpan(trace.pSpan);
        setCSpan(trace.cSpan);
    }

    public static Map<String, String> getCopyOfContextMap(){
        return MDC.getCopyOfContextMap();
    }

    public static void setContextMap(Map<String, String> contextMap) {
        if (contextMap != null) {
            MDC.setContextMap(contextMap);
        }
    }
    
    public static class Trace {
        private String traceId;
        private String pSpan;
        private String cSpan;
    
        public Trace(String traceId, String pSpan, String cSpan) {
            this.traceId = traceId;
            this.pSpan = pSpan;
            this.cSpan = cSpan;
        }
    
        public String getTraceId() {
            return traceId;
        }
    
        public String getpSpan() {
            return pSpan;
        }
    
        public String getcSpan() {
            return cSpan;
        }
    
        public void setTraceId(String traceId) {
            this.traceId = traceId;
        }
    
        public void setpSpan(String pSpan) {
            this.pSpan = pSpan;
        }
    
        public void setcSpan(String cSpan) {
            this.cSpan = cSpan;
        }
    }
    
    
    /********************************************************************
     * remove
     ********************************************************************/
    public static void remove(String key) {
        MDC.remove(key);
    }
    
    public static void clean() {
        MDC.clear();
    }
}
