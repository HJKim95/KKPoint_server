package com.adiscope.kkpoint.user;

import com.adiscope.kkpoint.attendance.AttendanceApiResponse;
import com.adiscope.kkpoint.attendance.Attendance;
import com.adiscope.kkpoint.attendance.AttendanceRepository;
import com.adiscope.kkpoint.common.custom_exception.NaverInvaildNameOrEmailException;
import com.adiscope.kkpoint.point_history.PointHistory;
import com.adiscope.kkpoint.point_history.PointHistoryRepository;
import com.adiscope.kkpoint.point_history.PointApiResponse;
import com.adiscope.kkpoint.common.Pagination;
import com.adiscope.kkpoint.common.Header;
import com.adiscope.kkpoint.attendance.AttendanceApiLogicService;
import com.adiscope.kkpoint.point_history.PointApiLogicService;
import com.adiscope.kkpoint.common.custom_exception.UserNotFoundException;
import com.adiscope.kkpoint.sign.AppleDTOs.MaterialsOfApplePublicKey;
import com.adiscope.kkpoint.sign.AppleDTOs.ApplePublicKeyResponse;
import com.adiscope.kkpoint.sign.GoogleDTO;
import com.adiscope.kkpoint.sign.KakaoDTOs.KakaoDTO;
import com.adiscope.kkpoint.sign.NaverDTOs.NaverDTO;
import com.adiscope.kkpoint.sign.SocialDTO;
import com.adiscope.kkpoint.user_withdraw.UserWithdraw;
import com.adiscope.kkpoint.user_withdraw.UserWithdrawRepository;
import com.adiscope.utils.JsonUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URI;
import java.time.LocalDate;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserApiLogicService {

    // 1. request data
    // 2. user 생성
    // 3. 생성된 데이터 -> UserApiResponse return

    private final AttendanceApiLogicService attendanceApiLogicService;

    private final PointApiLogicService pointApiLogicService;

    private final PointHistoryRepository pointHistoryRepository;

    private final AttendanceRepository attendanceRepository;

    private final UserRepository userRepository;

    private final UserWithdrawRepository userWithdrawRepository;

    @Transactional
    public Header<UserApiResponse> create(UserApiRequest request) {

        // 1. request data
        UserApiRequest userApiRequest = request;
        // 2. User 생성
        User user = User.builder()
                .socialType(userApiRequest.getSocialType())
                .name(userApiRequest.getName())
                .email(userApiRequest.getEmail())
                .enableAlarm(userApiRequest.getEnableAlarm())
                .enableAlarmDate(userApiRequest.getEnableAlarmDate())
                .build();

        // 3. 생성된 데이터 -> userApiResponse return
        return response(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public Header<UserApiResponse> read(Long id) {

        return userRepository.findById(id)
                .map(user -> response(user))
                .orElseGet(
                        ()->Header.ERROR("데이터 없음")
                );
    }

    @Transactional(readOnly = true)
    public Optional<User> readBySocialAndEmail(String socialType, String email) {
        return userRepository.findBySocialTypeAndEmail(socialType, email);
    }

    @Transactional(readOnly = true)
    public Header<List<AttendanceApiResponse>> attendInfo(Long id, String date) {

        // user
        User user = userRepository.getOne(id);

        UserApiResponse userApiResponse = response(user).getData();

        // Format "yyyy-MM-dd"
        LocalDate todayDate = LocalDate.parse(date);
        LocalDate startDate = LocalDate.parse(date).minusDays(1);
        LocalDate endDate = todayDate.plusMonths(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // attendance
        List<Attendance> attendanceList = (List<Attendance>) user.getAttendanceList().stream()
                .filter(t->
                        (t.getCreatedAt().isAfter(startDate) && t.getCreatedAt().isBefore(endDate)))
                .collect(Collectors.toList());

        List<AttendanceApiResponse> attendanceApiResponseList = attendanceList.stream()
                .map(attendance -> {
                    return attendanceApiLogicService.response(attendance);
                })
                .map(response -> response.getData())
                .collect(Collectors.toList());
        
        return Header.OK(attendanceApiResponseList);
    }

    @Transactional(readOnly = true)
    public Header<UserPointInfoApiResponse> pointInfo(Long id, Integer page, Integer size) {

        // user
        User user = userRepository.getOne(id);

        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());
        Page<PointHistory> pointHistoryPage = pointHistoryRepository.pagingPointHistory(id, pageable);

        Integer totalPoints = 0;
        totalPoints = user.getPointHistoryList().stream()
                .map(pointHistory -> pointHistory.getAmount().intValue())
                .collect(Collectors.toList())
                .stream().reduce((x,y) -> x+y).orElse(0); // 총 포인트만 계산


        List<PointApiResponse> pointApiResponseList = pointHistoryPage.stream()
                .map(pointHistory -> pointApiLogicService.response(pointHistory).getData())
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(pointHistoryPage.getTotalPages())
                .totalElements(pointHistoryPage.getTotalElements())
                .currentPage(pointHistoryPage.getNumber())
                .currentElements(pointHistoryPage.getNumberOfElements())
                .build();

        UserPointInfoApiResponse userPointInfoApiResponse = UserPointInfoApiResponse.builder()
                .totalPoints(totalPoints)
                .pointApiResponseList(pointApiResponseList)
                .build();

        return Header.OK(userPointInfoApiResponse, pagination);
    }

    @Transactional
    public Header<UserApiResponse> update(UserApiRequest request, Long id) {
        // 1. data
        UserApiRequest userApiRequest = request;

        // 2. id -> user 데이터 를 찾고
        Optional<User> optional = userRepository.findById(id);

        return optional.map(user -> {
            // 3. data -> update
            // idx도 같이 요청해주고 idx는 위에서 체크한다.
            user.setSocialType(userApiRequest.getSocialType())
                    .setName(userApiRequest.getName())
                    .setEmail(userApiRequest.getEmail())
                    .setEnableAlarm(userApiRequest.getEnableAlarm())
                    .setEnableAlarmDate(userApiRequest.getEnableAlarmDate())
            ;
            return user;

        })
                .map(user -> userRepository.save(user))             // update -> newUser
                .map(user -> response(user))                        // userApiResponse
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Transactional
    public Header delete(Long id) {
        // 1. id -> repository -> user
        Optional<User> optional = userRepository.findById(id);


        optional.map(user ->{
            // attendance 삭제
            user.getAttendanceList().stream().forEach(attendance -> attendanceRepository.delete(attendance));
            // pointHistory 삭제
            user.getPointHistoryList().stream().forEach(pointHistory -> pointHistoryRepository.delete(pointHistory));

            // 탈퇴한 유저 저장
            UserWithdraw userWithdraw = UserWithdraw.builder()
                    .uid(user.getIdx())
                    .email(user.getEmail())
                    .socialType(user.getSocialType())
                    .build();
            userWithdrawRepository.save(userWithdraw);

            // user 삭제
            userRepository.delete(user);
            return Header.OK();
        }).orElseGet(()->Header.ERROR("데이터 없음"));

        return Header.OK();

    }

    @Transactional(readOnly = true)
    public UserApiResponse findByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("["+userId+"] does not exist!!"));
        UserApiResponse userApiResponse = UserApiResponse.builder()
                .idx(user.getIdx())
                .socialType(user.getSocialType())
                .name(user.getName())
                .email(user.getEmail())
                .enableAlarm(user.getEnableAlarm())
                .enableAlarmDate(user.getEnableAlarmDate())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();

        BeanUtils.copyProperties(user, userApiResponse);
        return userApiResponse;
    }

    // name 추가 이유 : 애플 로그인은 서버단에서 인증할 때, 유저 이름을 알 수 없기 때문에, iOS에서 얻은 유저 이름을 받아와야함..
    public SocialDTO checkAccessToken(String socialType, String accessToken, String name ) throws IllegalAccessException, NaverInvaildNameOrEmailException {
        if (socialType.equals("kakao")) {
            // 카카오 accessToken -> 카카오 토큰 유효성 확인 및 프로필 정보 얻기
            // URI 준비
            URI url = URI.create("https://kapi.kakao.com/v2/user/me");
            HttpHeaders header = new HttpHeaders();
            header.add("Authorization", "Bearer " + accessToken);
            HttpEntity httpEntity = new HttpEntity(header);

            // Http Request 발싸
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

            // Json 파싱
            JsonUtil jsonUtil = new JsonUtil();
            KakaoDTO kakaoDTO = jsonUtil.convert(response.getBody().toString(), KakaoDTO.class);
            return SocialDTO.builder()
                    .name(kakaoDTO.getKakaoAccount().getProfile().getNickname())
                    .email(kakaoDTO.getKakaoAccount().getEmail())
                    .build();

        } else if (socialType.equals("google")) {
            // 구글 accessToken -> 구글 토큰 유효성 확인 및 프로필 정보 얻기
            // URI 준비
            URI url = URI.create("https://oauth2.googleapis.com/tokeninfo?id_token=" + accessToken);

            // Http Request 발싸
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, String.class);

            // Json 파싱
            JsonUtil jsonUtil = new JsonUtil();
            GoogleDTO googleDTO = jsonUtil.convert(response.getBody().toString(), GoogleDTO.class);
            return SocialDTO.builder().name(googleDTO.getName()).email(googleDTO.getEmail()).build();

        } else if (socialType.equals("apple")) {
            // 애플 accessToken -> 구글 토큰 유효성 확인 및 프로필 정보 얻기
            try {
                // 애플 accessToken의 header를 Base64, UTF-8 디코딩해서, 알맞는 key 재료를 알기위한 kid, alg 알아내기
                String headerOfAccessToken = accessToken.substring(0, accessToken.indexOf("."));
                JsonUtil jsonUtil = new JsonUtil();
                Map<String, String> header = jsonUtil.convert(new String(Base64.getDecoder().decode(headerOfAccessToken), "UTF-8"), Map.class);

                // 애플한테서 key들의 재료들 얻기 + 알맞는 key의 재료 찾기
                ApplePublicKeyResponse keys = ApplePublicKeyResponse.getApplePublicKeys();
                MaterialsOfApplePublicKey key = keys.getMatchedKeyBy(header.get("kid"), header.get("alg"))
                        .orElseThrow(() -> new NullPointerException("Failed get public key from apple's id server."));

                // 알맞는 key 재료로 부터, RS256 ( SHA-256 + RSA ) 암호화방식에서 사용하는 n, e 구하기
                byte[] nBytes = Base64.getUrlDecoder().decode(key.getN());
                byte[] eBytes = Base64.getUrlDecoder().decode(key.getE());
                BigInteger n = new BigInteger(1, nBytes);
                BigInteger e = new BigInteger(1, eBytes);

                // n, e를 이용하여 public key 만들기
                RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
                KeyFactory keyFactory = KeyFactory.getInstance(key.getKty());
                PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

                // 만들어진 public key로 accessToken의 body를 디코딩해서 유효한 유저 프로필 얻기
                Claims userInfo = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(accessToken).getBody();

                // 유저 프로필로 SocialDTO 만들어서 리턴
                // 애플은 서버인증시 이름을 알 수 없기때문에 request에 이름 추가..ㅠ
                return SocialDTO.builder().name(name).email(userInfo.get("email", String.class)).build();
            } catch (UnsupportedEncodingException e ) { // UTF-8 디코딩 에러
            } catch (NoSuchAlgorithmException e) { // getUnstance 에러
            } catch (InvalidKeySpecException e) { // generatePublic 에러
            }
        } else if (socialType.equals("naver")) {
            // 네이버 accessToken -> 네이버 토큰 유효성 확인 및 프로필 정보 얻기
            // URI 준비
            URI url = URI.create("https://openapi.naver.com/v1/nid/me");
            HttpHeaders header = new HttpHeaders();
            header.add("Authorization", accessToken);
            HttpEntity httpEntity = new HttpEntity(header);

            // Http Request 발싸
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

            // Json 파싱
            JsonUtil jsonUtil = new JsonUtil();
            NaverDTO naverDTO = jsonUtil.convert(response.getBody().toString(), NaverDTO.class);

            // 이름, 이메일 동의했는지 체크
            if (naverDTO.getResponse().getEmail() == null || naverDTO.getResponse().getName() == null) {
                throw new NaverInvaildNameOrEmailException("네이버 약관 이름이나 이메일이 체크 안됨");
            }
            return SocialDTO.builder()
                    .name(naverDTO.getResponse().getName())
                    .email(naverDTO.getResponse().getEmail())
                    .build();
        } else {
            throw new IllegalAccessException("잘못된 소셜 타입 입니다.");
        }
        return SocialDTO.builder().build();
    }

    private Header<UserApiResponse> response(User user){
        // user -> userApiResponse
        UserApiResponse userApiResponse = UserApiResponse.builder()
                .idx(user.getIdx())
                .socialType(user.getSocialType())
                .name(user.getName())
                .email(user.getEmail())
                .enableAlarm(user.getEnableAlarm())
                .enableAlarmDate(user.getEnableAlarmDate())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();

        // Header + data return
        return Header.OK(userApiResponse);
    }


}