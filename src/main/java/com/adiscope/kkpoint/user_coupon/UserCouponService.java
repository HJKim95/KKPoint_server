package com.adiscope.kkpoint.user_coupon;

import com.adiscope.kkpoint.common.Header;
import com.adiscope.kkpoint.common.Pagination;
import com.adiscope.kkpoint.common.custom_exception.CouponConflictException;
import com.adiscope.kkpoint.common.custom_exception.NoCouponAvailable;
import com.adiscope.kkpoint.coupon.Coupon;
import com.adiscope.kkpoint.coupon.CouponDto;
import com.adiscope.kkpoint.coupon.CouponRepository;
import com.adiscope.kkpoint.couponList.CouponList;
import com.adiscope.kkpoint.couponList.CouponListRepository;
import com.adiscope.kkpoint.point_history.PointHistory;
import com.adiscope.kkpoint.point_history.PointHistoryRepository;
import com.adiscope.kkpoint.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserCouponService {
    private final UserRepository userRepository;
    private final UserCouponRepository userCouponRepository;
    private final CouponRepository couponRepository;
    private final CouponListRepository couponListRepository;
    private final PointHistoryRepository pointHistoryRepository;

    // 유저 쿠폰 추가하기
    @Transactional
    public Header<UserCouponResponse> create(Long uid, Long idx) throws IllegalAccessException {

        Optional<CouponList> couponListOptional = couponListRepository.findById(idx);

        return couponListOptional.map(coup ->{
            List<Coupon> couponList = couponRepository.readMatchingListId(coup.getIdx());
            // 그 중에서 앞에 1개 추출
            if (couponList.stream().count() > 0) {
                Coupon coupon = couponList.get(0);
                CouponDto couponDto = CouponDto.builder()
                        .idx(coupon.getIdx())
                        .admin(coupon.getAdmin())
                        .couponNumber(coupon.getCouponNumber())
                        .couponName(coupon.getCouponName())
                        .couponListId(coupon.getCouponListId())
                        .homepage(coupon.getHomepage())
                        .homepageUrl(coupon.getHomepageUrl())
                        .isIssued(coupon.getIsIssued())
                        .build();

                if (!coupon.getIsIssued()) {
                    // 우선 테스트 이므로 주석처리.. @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                coupon.setIsIssued(true); // 1. Coupon DB 'isIssued' false -> true 변경
                    couponRepository.save(coupon);

                    // 2. PointHistory DB 소진내역 추가
                    PointHistory pointHistory = PointHistory.builder()
                            .amount(-coup.getPrice())
                            .content("쿠폰 구매")
                            .user(userRepository.getOne(uid))
                            .build();

                    pointHistoryRepository.save(pointHistory);


                    LocalDate date = LocalDate.of(LocalDate.now().getYear(), Month.DECEMBER, 31);
                    // 3. UserCoupon DB 사용내역 추가
                    UserCoupon userCoupon = UserCoupon.builder()
                            .couponId(couponDto.getCouponNumber())
//                            .validDate(LocalDate.now().plusMonths(6)) // 우선은 유효기간 6개월로 설정 -> 올해 말로 설정
                            .validDate(date)
                            .user(userRepository.getOne(uid))
                            .build();

                    return response(userCouponRepository.save(userCoupon));

                } else {
                    // 쿠폰을 확인한 순간 누가 구매하여 충돌이 난 경우
                    throw new CouponConflictException();

                }
            } else {
                // couponList = 0개인 경우
                throw new NoCouponAvailable();
            }
        }).orElseThrow(() -> new NoCouponAvailable());

    }

    // 한 유저 쿠폰 전부 읽기
    @Transactional(readOnly = true)
    public Header<List<UserCouponResponse>> getUserCouponsPaging(Long uid, Pageable p) {
        // 1. 디비에서 꺼내오기
        Page<UserCoupon> userCouponList = userCouponRepository.getUserCouponsPaging(uid, p);
        // 2. 리스폰으로 바꾸기
        List<UserCouponResponse> responseList = userCouponList.stream()
                .map(entity -> {
                    String couponNumber = entity.getCouponId();
                    Coupon coupon = couponRepository.findByCouponNumber(couponNumber);
                    return response(entity, coupon).getData();
                }).collect(Collectors.toList());

        // 3. 페이지 네이션 생성
        Pagination pagination = Pagination.builder()
                .totalPages(userCouponList.getTotalPages())
                .totalElements(userCouponList.getTotalElements())
                .currentPage(userCouponList.getNumber())
                .currentElements(userCouponList.getNumberOfElements())
                .build();

        // 4. 정상 리턴
        return Header.OK(responseList, pagination);
    }

    // 엔티티로 리스폰 만들기
    public Header<UserCouponResponse> response(UserCoupon userCoupon){
        // 1. 엔티티 -> 리스폰
        UserCouponResponse response = UserCouponResponse.builder()
                .idx(userCoupon.getIdx())
                .uid(userCoupon.getUser().getIdx())
                .couponId(userCoupon.getCouponId())
                .createdAt(userCoupon.getCreatedAt())
                .validDate(userCoupon.getValidDate())
                .build();

        // 2. 헤더 붙이기 & 리턴
        return Header.OK(response);
    }

    public Header<UserCouponResponse> response(UserCoupon userCoupon, Coupon coupon){
        // 1. 엔티티 -> 리스폰
        UserCouponResponse response = UserCouponResponse.builder()
                .idx(userCoupon.getIdx())
                .uid(userCoupon.getUser().getIdx())
                .admin(coupon.getAdmin())
                .couponName(coupon.getCouponName())
                .createdAt(userCoupon.getCreatedAt())
                .couponId(userCoupon.getCouponId())
                .validDate(userCoupon.getValidDate())
                .homepage(coupon.getHomepage())
                .homepageUrl(coupon.getHomepageUrl())
                .build();

        // 2. 헤더 붙이기 & 리턴
        return Header.OK(response);
    }
}
