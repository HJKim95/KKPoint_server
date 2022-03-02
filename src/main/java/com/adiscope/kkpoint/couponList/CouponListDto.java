package com.adiscope.kkpoint.couponList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponListDto {

    private Long idx;

    private String couponName;

    private String admin;

    private String dueDate;

    private String homepage;

    private String homepageUrl;

    private String validDate;

    private String itemDescription;

    private String itemCsDescription;

    private String deliverDescription;

    private String refundDescription;

    private Integer price;

}
