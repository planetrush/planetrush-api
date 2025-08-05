package com.planetrush.planetrush.verification.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class VerificationResultDto {

	private boolean verified;
	private double similarityScore;
	private String imgUrl;
	private Long planetId;
	private Long memberId;

}
