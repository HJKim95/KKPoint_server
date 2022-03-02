package com.adiscope.kkpoint.sign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequest {
    private String name;
    private String accessToken;
    private String socialType;
    private String uuid;
    private Boolean enableAlert;
}
