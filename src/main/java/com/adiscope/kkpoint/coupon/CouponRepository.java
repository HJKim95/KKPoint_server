package com.adiscope.kkpoint.coupon;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    @Query(value = "SELECT c FROM Coupon c WHERE c.couponListId = :idx AND isIssued = 0 ORDER BY c.idx DESC")
    List<Coupon> readMatchingListId(Long idx);

    Coupon findByCouponNumber(String couponId);

    @Query(value = "SELECT COUNT(*) FROM Coupon c WHERE c.couponListId = :idx AND isIssued = 0")
    Integer readAvailableCoupon(Long idx);
}