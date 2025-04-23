package com.lyranxi.link.user.http;

import com.lyranxi.link.user.token.UserToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * 管理httpContext
 *
 * @author ranxi
 * @date 2025-04-08 17:55
 */
public class HttpContextManage {

    public static void set(HttpServletRequest request,
                           HttpServletResponse response) {
        HttpContext.set(HttpContextDTO.builder().request(request).response(response).build());
    }

    public static void set(HttpServletRequest request,
                           HttpServletResponse response,
                           UserToken userToken) {
        HttpContext.set(HttpContextDTO.builder().request(request).response(response).userToken(userToken).build());
    }

    public static void remove() {
        HttpContext.remove();
    }

}
