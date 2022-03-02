package com.adiscope.kkpoint.couponList;

import com.adiscope.kkpoint.common.Header;
import com.adiscope.kkpoint.coupon.CouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Api(tags = {"CouponList"})
@RestController
@RequestMapping("/kkpoint/couponList")
public class CouponListController {

    @Autowired
    protected CouponListService couponListService;

    @ApiOperation(value = "모든 쿠폰 확인", notes = "현재 구매 가능한 모든 쿠폰을 보여준다. ( 쿠폰탭 화면에 나오는 모든 쿠 )")
    @GetMapping("/readAll")
    public Header<List<CouponListDto>> readAll() {
        return couponListService.readAll();
    }

    @ApiOperation(value = "구매가능한 모든 쿠폰 확인", notes = "현재 구매 가능한(발행되지 않은) 모든 쿠폰을 보여준다. ( isIssued = false )")
    @GetMapping("/available/readAll")
    public Header<Integer> readAllAvailableCoupon(@RequestParam(value = "couponListId") Long idx) {
        return couponListService.readAllAvailableCoupon(idx);
    }
}