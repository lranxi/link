package com.lyranxi.link.common.http;

import cn.hutool.core.util.StrUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * 特权url 直接放行
 *
 * @author ranxi
 * @date 2025-04-15 18:12
 */
public class PrivilegeUrl {

    private final static String ACTUATOR_PREFIX = "/actuator";
    private final Set<String> cache = new HashSet<>(32);

    public PrivilegeUrl() {
    }

    public PrivilegeUrl addUrl(String url) {
        this.cache.add(url);
        return this;
    }

    public PrivilegeUrl removeUrl(String url) {
        this.cache.remove(url);
        return this;
    }

    public boolean have(String url) {
        if (StrUtil.isNotBlank(url) && url.startsWith(ACTUATOR_PREFIX)) {
            return true;
        }
        return cache.contains(url);
    }


}
