package com.adiscope.kkpoint.sign.KakaoDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoAccount {
    private Profile profile;
    private String email;
}