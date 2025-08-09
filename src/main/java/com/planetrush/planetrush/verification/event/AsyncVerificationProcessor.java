package com.planetrush.planetrush.verification.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.planetrush.planetrush.infra.flask.res.FlaskResponseDto;
import com.planetrush.planetrush.infra.flask.util.FlaskApiClient;
import com.planetrush.planetrush.verification.service.dto.VerificationDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AsyncVerificationProcessor {

	private final FlaskApiClient flaskApiClient;
	private final ApplicationEventPublisher eventPublisher;

	@Async
	public void initiateSimilarityCheck(VerificationDto dto) {
		FlaskResponseDto res = flaskApiClient.verifyChallengeImg(dto.getStandardImgUrl(), dto.getUserImgUrl());
		eventPublisher.publishEvent(SaveVerificationResultEvent.builder()
			.verified(res.isVerified())
			.similarityScore(res.getSimilarityScore())
			.userImgUrl(dto.getUserImgUrl())
			.memberId(dto.getMemberId())
			.planetId(dto.getPlanetId())
			.build());
	}
}
