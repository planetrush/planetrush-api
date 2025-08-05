package com.planetrush.planetrush.member.service;

import com.planetrush.planetrush.member.service.dto.LoginDto;

public interface AuthService {

	/**
	 * 카카오 로그인을 진행합니다.
	 * @param accessToken 카카오에서 발급 받은 accessToken
	 * @return 자체적으로 발급한 accessToken, refreshToken
	 * @see LoginDto
	 */
	LoginDto kakaoLogin(String accessToken);

	/**
	 * 카카오 로그아웃을 진행합니다.
	 * <p>리프레시토큰을 만료시킵니다.</p>
	 * @param memberId 유저의 고유 id
	 */
	void kakaoLogout(Long memberId, String refreshToken);

	/**
	 * 회원을 탈퇴시킵니다.
	 * @param memberId 유저의 고유 id
	 */
	void withdrawnMember(Long memberId);
}
