package com.lyranxi.link.common.json.parser;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;

/**
 * @author fengxiaochun
 * @date 2022/3/11 12:06
 * @since 2203
 */
public class MapTypeReference<K, V> extends TypeReference<Map<K, V>> {
    public MapTypeReference() {
    }
}
