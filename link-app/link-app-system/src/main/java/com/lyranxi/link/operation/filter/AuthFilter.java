package com.lyranxi.link.operation.filter;

import com.lyranxi.link.common.enums.ClientEnum;
import com.lyranxi.link.common.enums.PlatformEnum;
import com.lyranxi.link.common.http.PrivilegeUrl;
import com.lyranxi.link.user.handler.AbstractUserAuthFilter;
import com.lyranxi.link.user.handler.AuthenticationHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ranxi
 * @date 2025-04-16 10:54
 */
@Slf4j
public class AuthFilter extends AbstractUserAuthFilter {

    public AuthFilter(AuthenticationHelper helper) {
        super(helper);
    }

    public AuthFilter(AuthenticationHelper helper, PrivilegeUrl privilegeUrl) {
        super(helper, privilegeUrl);
    }

    @Override
    protected PlatformEnum currentPlatform() {
        return PlatformEnum.OPERATION;
    }

    @Override
    protected boolean supportClientId(Integer clientId) {
        return ClientEnum.OPERATION.getId() == clientId;
    }
}
