package com.adiscope.kkpoint.coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;

}
