package com.adiscope.kkpoint.config.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenSet {
	private String accessToken;
	private String refreshToken;
}
