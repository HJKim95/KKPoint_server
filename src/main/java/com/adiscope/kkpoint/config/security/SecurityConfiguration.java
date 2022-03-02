package com.adiscope.kkpoint.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() // rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
                .csrf().disable() // rest api이므로 csrf 보안이 필요없으므로 disable처리.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token으로 인증할것이므로 세션필요없으므로 생성안함.
                .and()
                .authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크
                // 비로그인시에도(액세스 토큰 없이) 가능한 API들
                .antMatchers("/*/*/signup","/*/*/signin", "/*/*/refreshAccessToken").permitAll() // 로그인, 회원가입, 토큰 리프래쉬
                .antMatchers("/kkpoint/videos","/kkpoint/videos/*","/kkpoint/videos/channel/*","/kkpoint/videos/related/*").permitAll() // 전체영상, 채널영상 접근
                .antMatchers("/kkpoint/video/relatedItem/*").permitAll() // 연관상품 접
                .antMatchers("/kkpoint/couponList/*").permitAll() // 쿠폰리스트 접근
                .antMatchers("/kkpoint/couponList/available/*").permitAll() // 구매가능한 쿠폰 접근
                .antMatchers("/kkpoint/coupon/*").permitAll() // 쿠폰 접근 real
                .antMatchers("/kkpoint/notice").permitAll() // 공지, 이벤트 접근
                .antMatchers("/kkpoint/search","/kkpoint/search/*").permitAll() // 검색
                .antMatchers("/kkpoint/noticeEventManagement").permitAll() // 공지사항/이벤트 관련 임시로 누구나 접근 가능.
                .antMatchers("/kkpoint/iosVersionControll/*").permitAll() // 버전관리용으로써 parameter로 기존에 설정한 비번을 입력받기로함.
                .antMatchers("/kkpoint/appInfo/*").permitAll() // 버전관리, 정기점검 관리용
                .antMatchers("/kkpoint/channel/home").permitAll() // 채널 홈
                .antMatchers("/kkpoint/adiscope").permitAll() // Adiscope 보상 콜백

                .antMatchers("/actuator/**/*").permitAll() // health 는 누구나 접근가능
//                .antMatchers("/kkpoint/**/**/*").permitAll()
                .antMatchers(HttpMethod.GET, "/exception/**").permitAll() // 등록된 GET요청 리소스는 누구나 접근가능

                .anyRequest().hasRole("MEMBER") // 그외 나머지 요청은 모두 인증된 회원만 접근 가능
                .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler()) // 이유 모르겠음
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 토큰 없을 시, 잘못된 토큰일 시 CustomAuthenticationEntryPoint로 이동.
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // jwt token 필터를 id/password 인증 필터 전에 넣어라.
    }

    @Override // ignore swagger security config
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**");

    }
}
