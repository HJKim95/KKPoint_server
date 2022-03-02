package com.adiscope.kkpoint.user_coupon;


import com.adiscope.kkpoint.common.Header;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@Api(tags = {"UserCoupon"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/kkpoint")
public class UserCouponController {

    // 토큰에서 uid 가져오기
    protected String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        return userId;
    }

    @Autowired
    protected UserCouponService userCouponService;

    @ApiOperation(value = "유저의 쿠폰 보관함 목록 확인", notes = "유저의 쿠폰 보관함 목록을 확인한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/user_coupons")
    public Header<List<UserCouponResponse>> getUserCoupons(@RequestParam(value = "page") Integer page) {
        String userId = getUserId();
        Long uid = Long.parseLong(userId);
        Pageable p = PageRequest.of(page, 20);
        return userCouponService.getUserCouponsPaging(uid, p);
    }

    @ApiOperation(value = "유저의 쿠폰 구매", notes = "유저가 쿠폰을 구매한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping("/user_coupon")
    public Header<UserCouponResponse> create(@RequestParam(value = "couponListId") Long idx) throws IllegalAccessException {
        String userId = getUserId();
        Long uid = Long.parseLong(userId);
        return userCouponService.create(uid, idx);
    }

//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
//    })
//    @DeleteMapping("/user_coupon")
//    public Header delete(@RequestParam Long userCouponIdx) {
//        return userCouponService.delete(userCouponIdx);
//    }
}
