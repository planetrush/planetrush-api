package com.planetrush.planetrush.verification.facade.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class VerifyChallengeDto {

	@JsonProperty("isVerified")
	private boolean verified;
	private double similarityScore;

}
