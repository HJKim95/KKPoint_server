package com.adiscope.kkpoint.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    // 스프링이 아닌 시큐리티 단에서 에러 발생시 AuthenticationEntryPoint로 이동하며, 이들을 예외처리하기 위한 CustomAuthenticationEntryPoint
    // 토큰이 필요한 곳( = 멤버 인증이 필요한 곳 ) 에 토큰이 없거나 잘못 된 토큰으로 접근한 경우 여기 실행
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException, AccessDeniedException {
//        response.sendRedirect("/exception/entryPoint"); // 기존에 엔트리 포인트로 가면, 404 에러코드.
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, // 401 코드를 보내기 위한 처리
                "접근 권한이 없습니다.");
    }
}