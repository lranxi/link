package com.lyranxi.link.user.handler;

import com.lyranxi.link.common.annotation.Api;
import com.lyranxi.link.common.enums.PlatformEnum;
import com.lyranxi.link.user.bo.ApiCreateBO;
import com.lyranxi.link.user.feign.ApiFeign;
import com.lyranxi.link.web.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 接口注册器
 *
 * @author ranxi
 * @date 2025-04-17 16:09
 */
@Slf4j
public class ApiRegisterHandler {

    public static void register(ApplicationContext context, PlatformEnum platform) {
        Assert.notNull(context, "注册接口失败, ApplicationContext is null");
        batchSave(context, platform);
    }

    private static void batchSave(ApplicationContext context, PlatformEnum platform) {
        ApiFeign apiFeign = context.getBean(ApiFeign.class);
        Assert.notNull(apiFeign, "注册接口失败, ApiFeign is null");
        List<ApiCreateBO> apis = getApiInfo(context, platform);
        Result<Boolean> result = apiFeign.batchSave(apis);
        if (!result.success()) {
            log.warn("注册接口失败. {}", result.getMessage());
            throw new RuntimeException("注册接口失败:" + result.getMessage());
        }
    }


    private static List<ApiCreateBO> getApiInfo(ApplicationContext context, PlatformEnum platform) {
        RequestMappingHandlerMapping mapping = context.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = mapping.getHandlerMethods();
        List<ApiCreateBO> apis = new ArrayList<>(128);
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            HandlerMethod handlerMethod = entry.getValue();
            if (!handlerMethod.hasMethodAnnotation(Api.class)) {
                continue;
            }
            Method method = handlerMethod.getMethod();
            Api annotation = method.getAnnotation(Api.class);
            ApiCreateBO bo = new ApiCreateBO();
            bo.setName(annotation.name());
            bo.setUrl(getUri(entry.getKey()));
            bo.setMethod(getMethod(entry.getKey()));
            bo.setWhitelist(annotation.whitelist());
            bo.setScope(platform.getId());
            bo.setAuthor(annotation.author());
            apis.add(bo);
        }
        return apis;
    }


    private static String getUri(RequestMappingInfo request) {
        Assert.notNull(request, "注册接口失败,获取接口URL失败");
        if (request.getPatternsCondition() != null) {
            return request.getPatternsCondition().getPatterns().iterator().next();
        } else {
            PathPatternsRequestCondition pathPatternsCondition = request.getPathPatternsCondition();
            Assert.notNull(pathPatternsCondition, "注册接口失败, PathPatternsRequestCondition is null");
            PathPattern pattern = pathPatternsCondition.getPatterns().iterator().next();
            Assert.notNull(pattern, "注册接口失败, PathPattern is null");
            return pattern.getPatternString();
        }
    }


    private static String getMethod(RequestMappingInfo request) {
        RequestMethodsRequestCondition methodsCondition = request.getMethodsCondition();
        for (RequestMethod method : methodsCondition.getMethods()) {
            return method.name();
        }
        return "";
    }

}
