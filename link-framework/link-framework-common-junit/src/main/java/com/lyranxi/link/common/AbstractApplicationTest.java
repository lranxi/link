package com.lyranxi.link.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lyranxi.link.common.constant.TraceConstant;
import com.lyranxi.link.common.util.trace.MdcUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.AfterTestMethod;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ranxi
 * @date 2025-04-09 18:35
 */
@Slf4j
public abstract class AbstractApplicationTest {
    @Autowired
    protected TestRestTemplate restTemplate;

    /*******************************************************************************
     * 请求封装
     *******************************************************************************/

    protected <T> T get(String uri, Object requestObj, TypeReference<T> typeReference) {
        Map<String, String> headers = new HashMap<>(4);
        return exchange(HttpMethod.GET, uri, headers, requestObj, typeReference);
    }

    protected <T> T get(String uri, Object requestObj, ParameterizedTypeReference<T> typeReference) {
        Map<String, String> headers = new HashMap<>(4);
        return exchange(HttpMethod.GET, uri, headers, requestObj, typeReference);
    }

    protected <T> T post(String uri, Object requestObj, TypeReference<T> typeReference) {
        Map<String, String> headers = new HashMap<>(4);
        return exchange(HttpMethod.POST, uri, headers, requestObj, typeReference);
    }

    protected <T> T post(String uri, Object requestObj, ParameterizedTypeReference<T> typeReference) {
        Map<String, String> headers = new HashMap<>(4);
        return exchange(HttpMethod.POST, uri, headers, requestObj, typeReference);
    }

    /**
     * 使用自定义的Json反序列化对象
     *
     * @param method        请求方法
     * @param uri           路径
     * @param headerMap     请求头
     * @param requestObj    请求体对象
     * @param typeReference 请求结果对象类型
     * @param <T>           请求结果类
     * @return 结果对象
     */
    protected <T> T exchange(HttpMethod method, String uri, Map<String, String> headerMap, Object requestObj, TypeReference<T> typeReference) {
        HttpEntity httpEntity = getHttpEntity(headerMap, requestObj);
        log.info("{}: uri = {}, request = {}", method, uri, (requestObj != null ? JsonUtil.toJsonStr(requestObj) : ""));
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, method, httpEntity, new ParameterizedTypeReference<String>() {
        });
        log.info("{}: responseContent = {}", method, responseEntity.getBody());
        return JsonUtil.toObject(responseEntity.getBody(), typeReference);
    }

    /**
     * 使用spring的ParameterizedTypeReference直接返回响应对象
     *
     * @param method        请求方法
     * @param uri           路径
     * @param headerMap     请求头
     * @param requestObj    请求体对象
     * @param typeReference 请求结果对象类型
     * @param <T>           请求结果类
     * @return 结果对象
     */
    protected <T> T exchange(HttpMethod method, String uri, Map<String, String> headerMap, Object requestObj, ParameterizedTypeReference<T> typeReference) {
        HttpEntity httpEntity = getHttpEntity(headerMap, requestObj);
        log.info("{}: uri = {}, request = {}", method, uri, (requestObj != null ? JsonUtil.toJsonStr(requestObj) : ""));
        ResponseEntity<T> responseEntity = restTemplate.exchange(uri, method, httpEntity, typeReference);
        return responseEntity.getBody();
    }

    protected <T> HttpEntity<T> getHttpEntity(Map<String, String> headerMap, T body) {
        return body == null ? new HttpEntity<>(convertHttpHeaders(headerMap)) : new HttpEntity(body, convertHttpHeaders(headerMap));
    }

    private HttpHeaders convertHttpHeaders(Map<String, String> headerMap) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        if (!headerMap.containsKey(TraceConstant.X_TRACE_ID)) {
            headers.set(TraceConstant.X_TRACE_ID, MdcUtil.getTraceId());
        }
        if (!CollectionUtils.isEmpty(headerMap.entrySet())) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                headers.set(entry.getKey(), entry.getValue());
            }
        }
        extendHeaders(headers);
        return headers;
    }

    /**
     * 扩展请求头,子类可以重写此方法对每个请求头类容进行自定义
     * @author fengxiaochun
     * @date   2022/8/9 16:08
     * @param  headers  请求头
     * @since  2209
     **/
    protected void extendHeaders(HttpHeaders headers){}

    /*******************************************************************************
     * 普通常用方法
     *******************************************************************************/
    protected Long toLong(Object obj) {
        if (obj == null || obj.toString() == null || obj.toString().length() == 0) {
            return null;
        }
        return Long.parseLong(obj.toString());
    }

    protected String toString(Object obj) {
        if (obj == null || obj.toString() == null || obj.toString().length() == 0) {
            return null;
        }
        return obj.toString();
    }

    protected void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
    }

    protected long now() {
        return System.currentTimeMillis();
    }


    /*******************************************************************************
     * 计时器
     *******************************************************************************/
    public static StopWatch sw;

    //    @BeforeAll
    @BeforeTestClass
    public static void setUpBeforeClass() {
        sw = new StopWatch("AbstractApplicationTest");
    }

    //    @AfterAll
    @AfterTestClass
    public static void tearDownAfterClass() {
        log.info("**************************************************************");
        try {
            log.info("单元测试计时统计({}ms)：\n{}", sw.getTotalTimeMillis(), sw.prettyPrint());

        } catch (Exception e) {
        }
        log.info("**************************************************************");
    }

    //    @BeforeEach
    @BeforeTestMethod
    public void setUp() {
        MdcUtil.setTraceId();
        log.info("******************方法开始******************");
        try {
            sw.start(getClass().getSimpleName());
        } catch (Exception e) {
        }
    }

    //    @AfterEach
    @AfterTestMethod
    public void tearDown() {
        try {
            sw.stop();
        } catch (Exception e) {
        }
        log.info("******************方法结束******************");
    }


}
