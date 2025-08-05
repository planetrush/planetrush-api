package com.planetrush.planetrush.member.service;

import com.planetrush.planetrush.member.service.dto.ReissueDto;

public interface ReissueTokenService {

	/**
	 * 주어진 리프레시 토큰을 사용하여 새로운 액세스 토큰과 리프레시 토큰을 발급합니다.
	 * @param refreshToken 현재 사용자의 리프레시 토큰
	 * @return 새로운 액세스 토큰과 리프레시 토큰을 담고 있는 ReissueDto 객체
	 */
	ReissueDto reissueToken(String refreshToken);

}
