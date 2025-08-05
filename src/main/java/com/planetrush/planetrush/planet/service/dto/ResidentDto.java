package com.planetrush.planetrush.planet.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ResidentDto {

	private Long memberId;
	private String nickname;
	@JsonProperty("isQuerriedMember")
	private boolean querriedMember;
	private int verificationCnt;
	private double verificationContinuityPoint;

}
