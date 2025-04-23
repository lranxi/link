package com.lyranxi.link.common;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ranxi
 * @date 2025-04-09 18:34
 */
public class MapUrlParamsUtil {

    private static final String AND = "&";
    private static final String EQ = "=";

    private MapUrlParamsUtil() {
    }

    /**
     * 将url转换为map
     *
     * @param param 请求url
     * @return map
     */
    public static Map<String, Object> getParamMapByUrl(String param) {
        HashMap<String, Object> map = new HashMap<>();
        if (param == null || param.isEmpty()) {
            return map;
        }
        String[] params = param.split(AND);
        for (String s : params) {
            String[] p = s.split(EQ);
            if (p.length == 2) {
                map.put(p[0], p[1]);
            }
        }
        return map;
    }


    /**
     * 将map转换为url
     *
     * @param map 请求参数
     * @return String url
     */
    public static String getParamUrlByMap(Map<String, Object> map) {
        if (map == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append(entry.getKey()).append(EQ).append(entry.getValue());
            sb.append(AND);
        }
        String s = sb.toString();
        if (s.endsWith(AND)) {
            s = StringUtils.substringBeforeLast(s, AND);
        }
        return s;
    }


}
