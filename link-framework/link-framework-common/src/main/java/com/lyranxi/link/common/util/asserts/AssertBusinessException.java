package com.lyranxi.link.common.util.asserts;

import com.lyranxi.link.common.exception.BusinessException;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 断言工具
 *
 * @author ranxi
 * @date 2025-03-14 10:00
 */
public class AssertBusinessException {

    public static void nonNull(Object obj, String message) {
        if (Objects.isNull(obj)) {
            throw new BusinessException(message);
        }
    }

    public static void isNull(Object obj, String message) {
        if (Objects.nonNull(obj)) {
            throw new BusinessException(message);
        }
    }

    public static void isBlank(String str, String message) {
        if (str != null && !str.isEmpty()) {
            throw new BusinessException(message);
        }
    }

    public static void nonBlank(String str, String message) {
        if (str == null || str.isEmpty()) {
            throw new BusinessException(message);
        }
    }

    public static void isEmpty(Collection<Object> collection, String message) {
        if (collection != null && !collection.isEmpty()) {
            throw new BusinessException(message);
        }
    }

    public static void isEmpty(List<?> collection, String message) {
        if (collection != null && !collection.isEmpty()) {
            throw new BusinessException(message);
        }
    }

    public static void nonEmpty(Collection<?> collection, String message) {
        if (collection == null || collection.isEmpty()) {
            throw new BusinessException(message);
        }
    }

    public static void nonEmpty(List<?> list, String message) {
        if (list == null || list.isEmpty()) {
            throw new BusinessException(message);
        }
    }

    public static void isTrue(boolean flag, String message) {
        if (!flag) {
            throw new BusinessException(message);
        }
    }

}
