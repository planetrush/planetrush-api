package com.planetrush.planetrush.verification.event;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveVerificationResultEvent {

	private boolean verified;
	private double similarityScore;
	private String userImgUrl;
	private Long planetId;
	private Long memberId;
}
