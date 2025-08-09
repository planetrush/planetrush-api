package com.planetrush.planetrush.verification.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class VerificationDto {

	private String standardImgUrl;
	private String userImgUrl;
	private Long planetId;
	private Long memberId;

}
