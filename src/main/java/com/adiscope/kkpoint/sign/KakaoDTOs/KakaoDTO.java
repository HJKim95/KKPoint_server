package com.adiscope.kkpoint.sign.KakaoDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoDTO {
//    private Long id;
    private KakaoAccount kakaoAccount;
}

