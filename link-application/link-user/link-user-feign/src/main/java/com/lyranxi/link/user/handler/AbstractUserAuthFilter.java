package com.lyranxi.link.user.handler;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.lyranxi.link.common.constant.GlobalConstant;
import com.lyranxi.link.common.enums.PlatformEnum;
import com.lyranxi.link.common.http.PrivilegeUrl;
import com.lyranxi.link.common.util.trace.MdcUtil;
import com.lyranxi.link.user.http.HttpContextManage;
import com.lyranxi.link.user.token.UserToken;
import com.lyranxi.link.user.vo.ApiVO;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.Objects;

/**
 * @author ranxi
 * @date 2025-04-15 17:13
 */
@Slf4j
public abstract class AbstractUserAuthFilter implements Filter {

    private static final String NOT_FOUND = "{\"code\":404,\"message\":\"请求的资源不存在\",\"traceId\":\"{}\"}";
    private static final String CLIENT_ID_INVALID = "{\"code\":400,\"message\":\"请求头x-client-id不合法\",\"traceId\":\"{}\"}";
    private static final String TIMESTAMP_INVALID = "{\"code\":400,\"message\":\"请求头x-timestamp不合法\",\"traceId\":\"{}\"}";
    private static final String TOKEN_INVALID = "{\"code\":416,\"message\":\"令牌无效\",\"traceId\":\"{}\"}";
    private static final String UNAUTHORIZED = "{\"code\":407,\"message\":\"权限不足\",\"traceId\":\"{}\"}";
    private static final int TOKEN_LENGTH = 22;
    private static final long FIFTEEN_TIME_STAMP = 900000L;

    private final AuthenticationHelper helper;
    private final PrivilegeUrl privilegeUrl;

    public AbstractUserAuthFilter(AuthenticationHelper helper) {
        this.helper = helper;
        this.privilegeUrl = new PrivilegeUrl();
    }

    public AbstractUserAuthFilter(AuthenticationHelper helper, PrivilegeUrl privilegeUrl) {
        this.helper = helper;
        this.privilegeUrl = privilegeUrl;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 请求信息添加到上下文
        HttpContextManage.set(request, response);

        // 不检查权限
        if (privilegeUrl.have(request.getRequestURI())) {
            try {
                filterChain.doFilter(request, response);
            } finally {
                HttpContextManage.remove();
            }
        }

        // 检查请求头
        String error;
        if ((error = headerClientIdIsValid(request.getHeader(GlobalConstant.X_CLIENT_ID))) != null) {
            this.write(error, response);
            return;
        }
        if ((error = headerTimestampIsValid(request.getHeader(GlobalConstant.X_TIMESTAMP))) != null) {
            this.write(error, response);
            return;
        }
        // 检查权限
        String uri = request.getRequestURI();
        ApiVO api = helper.getApi(uri,request.getMethod(), currentPlatform().getId());
        if ((error = apiIsValid(api)) != null) {
            this.write(error, response);
            return;
        }

        // api是否白名单
        if (api.getWhitelist()) {
            filterChain.doFilter(request, response);
            return;
        }

        // 验证authorization
        String authorization = getAuthorization(request.getHeader(GlobalConstant.AUTHORIZATION));
        if ((error = authorizationIsValid(authorization)) != null) {
            this.write(error, response);
            return;
        }

        // 验证token
        UserToken tokenContext = helper.getUserToken(authorization);
        if ((error = tokenIsValid(api, tokenContext)) != null) {
            this.write(error, response);
            return;
        }

        // 保存token到线程中
        HttpContextManage.set(request, response, tokenContext);
        try {
            filterChain.doFilter(request, response);
        } finally {
            HttpContextManage.remove();
            // 刷新token有效时长
            helper.refreshTokenExpires(authorization);
        }


    }


    private void write(String error, HttpServletResponse response) {
        response.setStatus(HttpStatus.OK.value());
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            response.getWriter().write(error);
        } catch (Exception e) {
            log.error("写入响应数据失败.", e);
        }
    }

    protected abstract PlatformEnum currentPlatform();

    protected abstract boolean supportClientId(Integer clientId);

    protected String parseToken(String authorization) {
        return AuthorizationUtil.parseToken(authorization);
    }

    /**
     * 检查请求头x-client-id 是否合法
     *
     * @param clientIdStr 请求头x-client-id
     * @return boolean
     */
    protected String headerClientIdIsValid(String clientIdStr) {
        if (StrUtil.isBlank(clientIdStr) || !NumberUtil.isInteger(clientIdStr)) {
            if (log.isDebugEnabled()) {
                log.debug("header: {} is null or is not number", GlobalConstant.X_CLIENT_ID);
            }
            return getErrorMessage(CLIENT_ID_INVALID);
        }
        if (!supportClientId(Integer.valueOf(clientIdStr))) {
            if (log.isDebugEnabled()) {
                log.debug("client id not support");
            }
            return getErrorMessage(CLIENT_ID_INVALID);
        }
        return null;
    }

    /**
     * 检查请求头x-timestamp是否合法
     *
     * @param timestampStr 请求头x-timestamp
     * @return String 错误信息
     */
    protected String headerTimestampIsValid(String timestampStr) {
        if (StrUtil.isBlank(timestampStr) || !NumberUtil.isLong(timestampStr)) {
            return getErrorMessage(TIMESTAMP_INVALID);
        }
        if (subtractTime(System.currentTimeMillis(), Long.parseLong(timestampStr)) > FIFTEEN_TIME_STAMP) {
            return getErrorMessage(TIMESTAMP_INVALID);
        }
        return null;
    }

    /**
     * 检查接口是否有效
     *
     * @param api 接口信息
     * @return String 错误信息
     */
    protected String apiIsValid(ApiVO api) {
        if (api == null) {
            return getErrorMessage(NOT_FOUND);
        }
        if (Objects.equals(Boolean.FALSE, api.getValid())) {
            return getErrorMessage(NOT_FOUND);
        }
        return null;
    }

    /**
     * 获取token
     *
     * @param headerAuthorization 请求头中的token信息
     * @return String 真实的token
     */
    protected String getAuthorization(String headerAuthorization) {
        return AuthorizationUtil.parseToken(headerAuthorization);
    }

    /**
     * 请求头authorization是否合法
     *
     * @param authorization 请求头中的token信息,已经去掉Bearer
     * @return String 错误信息
     */
    protected String authorizationIsValid(String authorization) {
        if (StrUtil.isBlank(authorization) || authorization.length() != TOKEN_LENGTH) {
            return getErrorMessage(TOKEN_INVALID);
        }
        return null;
    }

    /**
     * 检查用户token是否合法
     *
     * @param api       接口信息
     * @param userToken 用户token
     * @return String 错误信息
     */
    protected String tokenIsValid(ApiVO api, UserToken userToken) {
        if (userToken == null) {
            if (log.isDebugEnabled()) {
                log.debug("userToken is null");
            }
            return getErrorMessage(TOKEN_INVALID);
        }

        if (userToken.getPlatform() == null || userToken.getPlatform() != currentPlatform().getId()) {
            if (log.isDebugEnabled()) {
                log.debug("platform invalid. token.platform: {}", userToken.getPlatform());
            }
            return getErrorMessage(TOKEN_INVALID);
        }
        // 检查角色权限是否可以访问当前接口
        if (!userToken.hasApi(api.getId())) {
            if (log.isDebugEnabled()) {
                log.debug("无访问权限");
            }
            return getErrorMessage(UNAUTHORIZED);
        }
        return null;
    }


    protected String getErrorMessage(String template) {
        return StrUtil.format(template, MdcUtil.getTraceId());
    }

    protected long subtractTime(long currentTime, long time) {
        return currentTime - time > 0 ? currentTime - time : time - currentTime;
    }

}
