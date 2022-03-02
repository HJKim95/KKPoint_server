package com.adiscope.kkpoint.user_coupon;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
    @Query("SELECT uc FROM UserCoupon uc WHERE uc.user.idx = :uid ")
    Page<UserCoupon> getUserCouponsPaging(Long uid, Pageable pageable);
}
