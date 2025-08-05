package com.planetrush.planetrush.verification.service;

import com.planetrush.planetrush.verification.service.dto.VerificationResultDto;

public interface VerificationService {

	/**
	 * 행성의 고유 id로 행성의 인증 기준 이미지 url을 가져옵니다.
	 * @param planetId 행성의 고유 id
	 * @return 인증 기준 이미지 url
	 */
	String getStandardImgUrlByPlanetId(Long planetId);

	/**
	 * 사진 url과 유사도, 인증 여부를 저장합니다.
	 * @param dto 사진 url과 유사도, 인증 여부, 사용자 고유 id, 행성의 고유 id
	 */
	void saveVerificationResult(VerificationResultDto dto);

}
