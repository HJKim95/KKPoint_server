package com.adiscope.kkpoint.sign;

import com.adiscope.kkpoint.common.Header;
import com.adiscope.kkpoint.config.token.BaseController;
import com.adiscope.kkpoint.config.token.CustomUserDetailsService;
import com.adiscope.kkpoint.config.token.TokenSet;
import com.adiscope.kkpoint.config.security.JwtTokenProvider;
import com.adiscope.kkpoint.config.token.enums.ActionCode;
import com.adiscope.kkpoint.config.token.loginHistory.LoginHistoryService;
import com.adiscope.kkpoint.config.token.tokenStore.TokenStoreService;
import com.adiscope.kkpoint.user.User;
import com.adiscope.kkpoint.user.UserApiLogicService;
import com.adiscope.kkpoint.user.UserApiRequest;
import com.adiscope.kkpoint.user.UserApiResponse;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Api(tags = {"Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/kkpoint/user")
public class SignController extends BaseController {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private final UserApiLogicService userApiLogicService;
    private final LoginHistoryService loginHistoryService;
    private final TokenStoreService tokenStoreService;

    // 로그인 시도.
    @ApiOperation(value = "SNS로그인 시도", notes = "SNS로그인 정보를 검증하고, JWT토큰 발급 및 로그인인지 회원가입인지 판단한다.(apple 만 name 필요) ")
    @PostMapping(value = "/signin")
    public Header<SignInResponse> signin(@RequestBody SignInRequest request) {
        // SNS 엑세스 토큰 유효성 검증 및 유저 프로필 받기
        // 유효하지 않다면 에러 404 BadRequest
        SocialDTO socialDTO;
        try {
            socialDTO = userApiLogicService.checkAccessToken(request.getSocialType(), request.getAccessToken(), request.getName());
        } catch ( IllegalAccessException e ) {
            return Header.ERROR("잘못된 소셜 타입 입니다.");
        }
        // 이름과 이메일로 유저 정보 가져오기. 없는 유저면 null
        Optional<User> user = userApiLogicService.readBySocialAndEmail(request.getSocialType(), socialDTO.getEmail());
        // 이름 + 이메일 + 회원가입 필요여부 + 토큰정보를 넣을 리스폰 생성
        SignInResponse signInResponse = new SignInResponse();

        if ( user.isPresent() ) { // 이미 있는 유저 -> 바로 로그인.
            // 토큰 생성을 위한 uid
            Long uid = user.get().getIdx();
            // MEMBER Role 부여하기..; 왜필요한진 잘 모르겠음 일단 ㄱ
            UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(uid));
            // uuid 체크 ( 여러 기기 로그인 방지 )
//            loginHistoryService.register(uid, ActionCode.login, request.getUuid());
            // uid + 멤버가 부여된 유저 디테일 + 서버의 공개키로 JWT 토큰 생성
            TokenSet tokenSet = jwtTokenProvider.createToken(uid, userDetails.getAuthorities());
            signInResponse.setTokenSet(tokenSet);

            // uid, 이름, 이메일, 회원가입 필요여 설정
            signInResponse.setUid(uid);
            signInResponse.setName(user.get().getName()); // 로그인시 디비에서 가져오게
            signInResponse.setEmail(socialDTO.getEmail());
            signInResponse.setNeedSignUp(false);
            signInResponse.setSocialType(request.getSocialType());
        } else { // 없는 유저 -> 회원가입 해주세요.
            // 회원가입 필요 여부 : true
            signInResponse.setNeedSignUp(true);
        }
        return Header.OK(signInResponse);
    }

    // 회원가입.
    @ApiOperation(value = "회원가입", notes = "회원가입을 한다.")
    @PostMapping(value = "/signup")
    public Header<SignUpResponse> signup(@RequestBody SignUpRequest request) {
        // SNS 엑세스 토큰 유효성 검증 및 유저 프로필 받기
        // 유효하지 않다면 에러 404 BadRequest
        SocialDTO socialDTO;
        try {
            socialDTO = userApiLogicService.checkAccessToken(request.getSocialType(), request.getAccessToken(), request.getName());
        } catch ( IllegalAccessException e ) {
            return Header.ERROR("잘못된 소셜 타입 입니다.");
        }

        // 이름과 이메일로 유저 정보 가져오기. 이미 있는지 체크
        Optional<User> user = userApiLogicService.readBySocialAndEmail(request.getSocialType(), socialDTO.getEmail());
        if ( user.isPresent() ) {
            return Header.ERROR("소셜 타입 : " + request.getSocialType() + " / email : " + socialDTO.getEmail() +" / 이미 회원가입 된 유저입니다.");
        }

        // 새로운 정보로 유저 만들어서 DB에 저장
        UserApiRequest userApiRequest = UserApiRequest.builder()
                .socialType(request.getSocialType())
                .name(socialDTO.getName())
                .email(socialDTO.getEmail())
                .enableAlarm(request.getEnableAlert())
                .enableAlarmDate(LocalDate.now())
                .build();
        UserApiResponse userApiResponse = userApiLogicService.create(userApiRequest).getData();

        // 토큰 생성을 위한 uid
        Long uid = userApiResponse.getIdx();
        // MEMBER Role 부여하기..; 왜필요한진 잘 모르겠음 일단 ㄱ
        UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(uid));
        // uuid 체크 ( 여러 기기 로그인 방지 )
//        loginHistoryService.register(uid, ActionCode.login, request.getUuid());
        // uid + 멤버가 부여된 유저 디테일 + 서버의 공개키로 JWT 토큰 생성
        TokenSet tokenSet = jwtTokenProvider.createToken(uid, userDetails.getAuthorities());

        return Header.OK(SignUpResponse.builder()
                .name(socialDTO.getName())
                .email(socialDTO.getEmail())
                .uid(uid)
                .tokenSet(tokenSet)
                .socialType(request.getSocialType())
                .build());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "로그아웃", notes = "회원 로그아웃한다. 또한 refresh token을 저장소에서 삭제한다.")
    @PostMapping(value = "/signout")
    public Header<Boolean> signout(
            @ApiParam(value = "기기ID", required = true) @RequestParam String deviceId
    ) {
        // Authentication 로부터 uid 를 받아옴
        String userId = getUserId();
        Long uid = Long.parseLong(userId);
        // 로그아웃 히스토리를 남긴다..
//        loginHistoryService.register(uid, ActionCode.logout, deviceId);
        // refreshToken을 저장소에서 삭제한다
        tokenStoreService.deleteByMemberIdx(uid);
        return Header.OK(true);
    }

    @ApiOperation(value = "토큰재발급", notes = "토큰을 재발급한다") // = 이게 자동로그인이랑 같네
    @PostMapping("/refreshAccessToken")
    public Header<TokenSet> refreshAccessToken(@ApiParam(value = "refreshToken ", required = true) @RequestParam String refreshToken) {
        TokenSet tokenSet = jwtTokenProvider.refreshAccessToken(refreshToken);
        return Header.OK(tokenSet);
    }
}
