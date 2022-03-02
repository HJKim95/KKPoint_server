package com.adiscope.kkpoint.coupon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponDto {

    private Long idx;

    private String admin;

    private String couponNumber;

    private String couponName;

    private Long couponListId;

    private String homepage;

    private String homepageUrl;

    private Boolean isIssued;
}