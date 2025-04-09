package com.lyranxi.link.user.http;

import com.lyranxi.link.user.token.UserToken;
import lombok.Builder;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * 请求上下文
 * @author ranxi
 * @date 2025-04-07 18:11
 */
@Data
@Builder
public class HttpContextDTO implements Serializable {

    private UserToken userToken;
    private HttpServletRequest request;
    private HttpServletResponse response;
}
