package com.planetrush.planetrush.core.jwt.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtToken {

	private String accessToken;
	private String refreshToken;

}