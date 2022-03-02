package com.adiscope.kkpoint.couponList;

import com.adiscope.kkpoint.common.Header;
import com.adiscope.kkpoint.coupon.Coupon;
import com.adiscope.kkpoint.coupon.CouponDto;
import com.adiscope.kkpoint.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CouponListService {

    private final CouponListRepository couponListRepository;
    private final CouponRepository couponRepository;

    // 쿠폰 리스 전체 읽기
    @Transactional(readOnly = true)
    public Header<List<CouponListDto>> readAll() {
        // 1. 디비에서 꺼내오기
        List<CouponList> couponList = couponListRepository.findAll();
        // 2. 리스폰으로 바꾸기
        List<CouponListDto> couponDtoList = couponList.stream()
                .map(entity -> {
                    return response(entity).getData();

                }).collect(Collectors.toList());
        // 3. 정상 리턴
        return Header.OK(couponDtoList);
    }

    @Transactional(readOnly = true)
    public Header<Integer> readAllAvailableCoupon(Long couponListId) {

        Integer count = couponRepository.readAvailableCoupon(couponListId);
        // 3. 정상 리턴
        return Header.OK(count);
    }

    // 엔티티로 리스폰 만들기
    public Header<CouponListDto> response(CouponList couponList){
        // 1. 엔티티 -> 리스폰
        CouponListDto couponListDto = CouponListDto.builder()
                .idx(couponList.getIdx())
                .couponName((couponList.getCouponName()))
                .admin(couponList.getAdmin())
                .dueDate(couponList.getDueDate())
                .homepage(couponList.getHomepage())
                .homepageUrl(couponList.getHomepageUrl())
                .validDate(couponList.getValidDate())
                .itemDescription(couponList.getItemDescription())
                .itemCsDescription(couponList.getItemCsDescription())
                .deliverDescription(couponList.getDeliverDescription())
                .refundDescription(couponList.getRefundDescription())
                .price(couponList.getPrice())
                .build();

        // 2. 헤더 붙이기 & 리턴
        return Header.OK(couponListDto);
    }


}