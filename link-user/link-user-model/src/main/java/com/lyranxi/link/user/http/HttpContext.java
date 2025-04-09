package com.lyranxi.link.user.http;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.lyranxi.link.common.bo.CoreBO;
import com.lyranxi.link.common.constant.GlobalConstant;
import com.lyranxi.link.common.enums.BaseResponseCodeEnum;
import com.lyranxi.link.common.enums.TenantTypeEnum;
import com.lyranxi.link.common.exception.BizException;
import com.lyranxi.link.common.exception.ServiceInternalErrorException;
import com.lyranxi.link.common.page.PageInfo;
import com.lyranxi.link.common.util.asserts.AssertBusinessException;
import com.lyranxi.link.user.token.UserTenantInfo;
import com.lyranxi.link.user.token.UserToken;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

/**
 * @author ranxi
 * @date 2025-04-08 17:56
 */
@Slf4j
public class HttpContext {

    private static final ThreadLocal<HttpContextDTO> THREAD_LOCAL = new InheritableThreadLocal<>();

    private HttpContext() {
    }

    static void set(HttpContextDTO dto) {
        THREAD_LOCAL.set(dto);
    }

    static void remove() {
        THREAD_LOCAL.remove();
    }

    static HttpContextDTO get() {
        return THREAD_LOCAL.get();
    }


    /**
     * 快速构建CoreBO
     *
     * @param data 参数
     * @return CoreBO
     */
    public static <T> CoreBO<T> build(T data) {
        if (data == null) {
            throw new ServiceInternalErrorException("CoreBO.content can not be null");
        }
        return CoreBO.fromContent(data)
                .setTenantId(Token.getTenantId())
                .setStoreId(Token.getStoreId())
                .setOperatorId(Token.getCurrentUserId())
                .setClientId(Header.getClientId());
    }

    public static CoreBO<?> build() {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("_a_", 1);
        return CoreBO.fromContent(map)
                .setTenantId(Token.getTenantId())
                .setStoreId(Token.getStoreId())
                .setOperatorId(Token.getCurrentUserId())
                .setClientId(Header.getClientId());
    }


    public static <T> CoreBO<T> buildIncludePageInfo(T data) {
        if (data == null) {
            throw new ServiceInternalErrorException("CoreBO.content can not be null");
        }
        return build(data).setPageInfo(Header.getPageInfo());
    }


    public static class Token {

        private Token() {
        }

        public static UserToken get() {
            return THREAD_LOCAL.get().getUserToken();
        }

        public static UserTenantInfo getUserTenantInfo() {
            return Optional.ofNullable(get())
                    .map(UserToken::getUserTenant)
                    .orElse(null);
        }

        public static Integer getPlatform() {
            return Optional.ofNullable(get())
                    .map(UserToken::getPlatform)
                    .orElse(null);
        }

        public static Integer getTenantType() {
            return Optional.ofNullable(getUserTenantInfo())
                    .map(UserTenantInfo::getTenantType)
                    .orElse(null);
        }


        private static String getStoreId() {
            UserTenantInfo userTenantInfo = getUserTenantInfo();
            if (userTenantInfo == null) {
                return null;
            }

            if (!Objects.equals(userTenantInfo.getTenantType(), TenantTypeEnum.STORE.getType())) {
                return null;
            }
            return userTenantInfo.getStoreId();
        }

        private static String getTenantId() {
            UserTenantInfo userTenantInfo = getUserTenantInfo();
            if (userTenantInfo == null) {
                return null;
            }
            if (!Objects.equals(userTenantInfo.getTenantType(), TenantTypeEnum.GROUP.getType())) {
                return null;
            }
            return userTenantInfo.getTenantId();
        }


        public static String getStoreIdMust() {
            String storeId = getStoreId();
            AssertBusinessException.nonBlank(storeId, "门店ID不存在");
            return storeId;
        }

        public static String getTenantIdMust() {
            String tenantId = getTenantId();
            AssertBusinessException.nonBlank(tenantId, "租户ID不存在");
            return tenantId;
        }

        public static Long getCurrentUserId() {
            return Optional.ofNullable(getUserTenantInfo())
                    .map(UserTenantInfo::getUserId)
                    .orElse(null);
        }

        public static Long getCurrentUserIdMust() {
            Long userId = getCurrentUserId();
            AssertBusinessException.nonNull(userId, "用户ID不存在");
            return userId;
        }

        public static String getCurrentUserNickname() {
            return Optional.ofNullable(getUserTenantInfo())
                    .map(UserTenantInfo::getNickname)
                    .orElse(null);
        }

    }

    public static class Header {

        private Header() {
        }

        public static HttpServletRequest getRequest() {
            return THREAD_LOCAL.get().getRequest();
        }

        public static String getString(String key) {
            HttpServletRequest request = getRequest();
            if (request == null) {
                return null;
            }
            return request.getHeader(key);
        }

        public static Long getLong(String key) {
            String s = getString(key);
            if (StrUtil.isBlank(s)) {
                return null;
            }
            try {
                return Long.valueOf(s);
            } catch (Exception e) {
                log.error("String: {} -> Long failed. Cause by: {}", s, e.getMessage());
                return null;
            }
        }

        public static Integer getInteger(String key) {
            String s = getString(key);
            if (StrUtil.isBlank(s)) {
                return null;
            }
            try {
                return Integer.valueOf(s);
            } catch (Exception e) {
                log.error("String: {} -> Integer failed. Cause by: {}", s, e.getMessage());
                return null;
            }
        }

        public static Integer getClientId() {
            return Header.getInteger(GlobalConstant.X_CLIENT_ID);
        }

        public static Long getDeviceId() {
            return Header.getLong(GlobalConstant.X_DEVICE_ID);
        }

        public static PageInfo getPageInfo() {
            HttpServletRequest request = getRequest();
            PageInfo pageInfo = new PageInfo();
            String pageNum = request.getParameter(GlobalConstant.PAGE_NO);
            String pageSize = request.getParameter(GlobalConstant.PAGE_SIZE);
            String maxId = request.getParameter(GlobalConstant.MAX_ID);
            String maxUploadTime = request.getParameter(GlobalConstant.MAX_UPDATE_TIME);
            try {
                if (StrUtil.isNotBlank(pageNum)) {
                    pageInfo.setPageNum(Integer.parseInt(pageNum));
                }
            } catch (NumberFormatException e) {
                throw new BizException(BaseResponseCodeEnum.PARAM_FORMAT_ERROR.getCode(), "分页参数错误");
            }

            try {
                if (StrUtil.isNotBlank(pageSize)) {
                    pageInfo.setPageSize(Integer.parseInt(pageSize));
                }
            } catch (NumberFormatException e) {
                throw new BizException(BaseResponseCodeEnum.PARAM_FORMAT_ERROR.getCode(), "分页参数错误");
            }

            try {
                if (StrUtil.isNotBlank(maxId)) {
                    pageInfo.setMaxId(Long.valueOf(maxId));
                }
            } catch (NumberFormatException e) {
                throw new BizException(BaseResponseCodeEnum.PARAM_FORMAT_ERROR.getCode(), "分页参数错误");
            }

            try {
                if (StrUtil.isNotBlank(maxUploadTime)) {
                    pageInfo.setMaxUpdateTime(LocalDateTimeUtil.of(Long.parseLong(maxUploadTime)));
                }
            } catch (NumberFormatException e) {
                throw new BizException(BaseResponseCodeEnum.PARAM_FORMAT_ERROR.getCode(), "分页参数错误");
            }
            return pageInfo;
        }

    }
}