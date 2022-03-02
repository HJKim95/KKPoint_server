package com.adiscope.kkpoint.coupon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "kk_coupon")
@Builder
@Accessors(chain = true)
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String admin;

    private String couponNumber;

    private String couponName;

    private Long couponListId;

    private String homepage;

    private String homepageUrl;

    private Boolean isIssued;

}
