package com.adiscope.kkpoint.sign;

import com.adiscope.kkpoint.config.token.TokenSet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResponse {
    private TokenSet tokenSet;
    private String name;
    private String email;
    private String socialType;
    private Long uid;
}
