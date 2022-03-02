package com.adiscope.kkpoint.config.security;

import com.adiscope.kkpoint.common.custom_exception.NotExistRefreshTokenException;
import com.adiscope.kkpoint.common.custom_exception.RefreshTokenExpiredException;
import com.adiscope.kkpoint.user.UserApiLogicService;
import com.adiscope.kkpoint.user.UserApiResponse;
import com.adiscope.kkpoint.config.token.tokenStore.TokenStore;
import com.adiscope.kkpoint.config.token.TokenSet;
import com.adiscope.kkpoint.config.token.CustomUserDetailsService;
import com.adiscope.kkpoint.config.token.tokenStore.TokenStoreService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider { // JWT 토큰을 생성 및 검증 모듈

    @Value("${spring.jwt.secret}")
    private String secretKey;

    public static final long tokenValidMillisecond = 1000L * 60 * 60 * 6; // 6시간만 토큰 유효 - accessToken 유효시간

    public static final long tokenValidMillisecondForRefresh = 1000L * 60 * 60 * 2160; // 2160시간(3개월)만 토큰 유효 - refreshToken 유효시간

    private final CustomUserDetailsService userDetailsService;
//    private final MemberService memberService;

    private final UserApiLogicService userApiLogicService;
    private final TokenStoreService tokenStoreService;

    @PostConstruct
    protected void init() {
        try {
            secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }
    }

    // Jwt 토큰 생성
    public TokenSet createToken(Long uid, Collection<? extends GrantedAuthority> authorities) {
        String idxString = String.valueOf(uid);
        Claims claims = Jwts.claims().setSubject(idxString);

        List<String> roles = new ArrayList<>();
        for (GrantedAuthority grantedAuthority : authorities) {
            roles.add(grantedAuthority.getAuthority());
        }

        claims.put("roles", roles);
        Date now = new Date();

        String accessToken = Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret값 세팅
                .compact();

        String refreshToken = Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(new Date(now.getTime() + tokenValidMillisecondForRefresh)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret값 세팅
                .compact();
        TokenStore tokenStore = TokenStore.builder().memberIdx(uid).refreshToken(refreshToken).build();
        tokenStoreService.register(tokenStore);

        TokenSet tokenSet = TokenSet.builder().accessToken(accessToken).refreshToken(refreshToken).build();

        return tokenSet;
    }

    public TokenSet refreshAccessToken(String refreshToken) {
        //refreshToken의 만료기간이 남았는지 확인하고,
        if (!validateToken(refreshToken)) {
            throw new RefreshTokenExpiredException();
        }
        //DB로부터 refreshToken 유효한지 조회
        TokenStore tokenStore = tokenStoreService.findByRefreshToken(refreshToken);
        if (tokenStore == null) {
            throw new NotExistRefreshTokenException();
        }
        tokenStoreService.deleteByRefreshToken(refreshToken);

        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserId(refreshToken));
        Long id = Long.parseLong(userDetails.getUsername());
        UserApiResponse userApiResponse = userApiLogicService.findByUserId(id);

        TokenSet tokenSet = createToken(userApiResponse.getIdx(), userDetails.getAuthorities());

        return tokenSet;
    }

    // Jwt 토큰으로 인증 정보를 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Jwt 토큰에서 회원 구별 정보 추출
    public String getUserId(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 파싱 : "X-AUTH-TOKEN: jwt토큰"
    public String resolveToken(HttpServletRequest req) {
        return req.getHeader("X-AUTH-TOKEN");
    }

    // Jwt 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
