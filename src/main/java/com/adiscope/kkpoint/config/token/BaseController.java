package com.adiscope.kkpoint.config.token;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by 최경근(keun0912@neowiz.com) on 2020-03-26.
 */
public class BaseController {

    protected String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        return userId;
    }
}
