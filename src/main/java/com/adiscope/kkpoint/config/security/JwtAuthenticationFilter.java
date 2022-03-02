package com.adiscope.kkpoint.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtAuthenticationFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    // Jwt Provier 주입
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // Request로 들어오는 Jwt Token의 유효성을 검증(jwtTokenProvider.validateToken)하는 filter를 filterChain에 등록합니다.
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // 리퀘스트 헤더에서 토큰을 가져옴 ( 헤더에서 X-AUTH-TOKEN을 가져감 )
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        // 토큰이 들어있고, 유효한 토큰일 경우, 보안 컨텍스트에 저장.
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication auth = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        // 다음 체인으로~
        filterChain.doFilter(request, response);
    }
}
