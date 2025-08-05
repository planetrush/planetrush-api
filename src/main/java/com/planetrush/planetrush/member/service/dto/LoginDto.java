package com.planetrush.planetrush.member.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginDto {

	private String nickname;
	private String accessToken;
	private String refreshToken;

}
