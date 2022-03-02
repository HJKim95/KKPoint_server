package com.adiscope.kkpoint.config.security;

import com.adiscope.kkpoint.config.security.InvalidIpException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 최경근(keun0912@neowiz.com) on 2020-03-27.
 */
public class ManageBaseController {

    @Value("${access.ip.to.manage}")
    protected String accessIps;

    protected void accessCheck(HttpServletRequest request) {
        String ip = getIp(request);

        String[] ips = StringUtils.split(accessIps, ",");
        if(ips != null) {
            boolean check = false;
            for(String accessIp : ips) {
                if (new IpAddressMatcher(accessIp).matches(ip)) {
                    check = true;
                    break;
                }
            }
            if(!check) {
                throw new InvalidIpException("invalid ip [" + ip + "]");
            }
        }



    }

    private String getIp(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP"); // 웹로직
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        return ip;

    }

}
