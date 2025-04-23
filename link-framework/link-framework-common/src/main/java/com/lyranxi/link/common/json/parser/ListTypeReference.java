package com.lyranxi.link.common.json.parser;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

/**
 * @author fengxiaochun
 * @date 2022/3/11 12:05
 * @since 2203
 */
public class ListTypeReference<T> extends TypeReference<List<T>> {
    public ListTypeReference() {
    }
}
