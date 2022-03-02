package com.adiscope.kkpoint.user_coupon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCouponResponse {
    private Long idx;

    private Long uid;

    private String admin;

    private String couponName;

    private LocalDateTime createdAt;

    private String couponId;

    private LocalDate validDate;

    private String homepage;

    private String homepageUrl;
}
