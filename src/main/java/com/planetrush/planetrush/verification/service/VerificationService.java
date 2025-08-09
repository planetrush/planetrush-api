package com.planetrush.planetrush.verification.service;

import com.planetrush.planetrush.verification.event.SaveVerificationResultEvent;

public interface VerificationService {

	/**
	 * 행성의 고유 id로 행성의 인증 기준 이미지 url을 가져옵니다.
	 * @param planetId 행성의 고유 id
	 * @return 인증 기준 이미지 url
	 */
	String getStandardImgUrlByPlanetId(Long planetId);

	/**
	 * 사진 url과 유사도, 인증 여부를 저장합니다.
	 * @param event 유사도 측정 후 결과가 담긴 이벤트
	 */
	void saveVerificationResult(SaveVerificationResultEvent event);

	/**
	 * 오늘 유저가 행성에 대한 인증 여부를 반환합니다.
	 * @param memberId 유저의 고유 id
	 * @param planetId 행성의 고유 id
	 * @return 인증 여부
	 */
	boolean getTodayRecord(Long memberId, Long planetId);

	void verifyTodayChallenge(Long memberId, Long planetId, String targetImgUrl);
}
