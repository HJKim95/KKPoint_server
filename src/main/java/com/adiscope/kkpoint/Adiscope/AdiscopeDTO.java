package com.adiscope.kkpoint.Adiscope;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdiscopeDTO {

    private Long idx;

    private String transactionId;

    private String signature;

    private String unitId;

    private String userId;

    private String adid;

    private String rewardUnit;

    private String network;

    private String rewardAmount;

    private String adtype;

    private String adname;
}
