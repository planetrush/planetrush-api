package com.planetrush.planetrush.member.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planetrush.planetrush.core.jwt.JwtTokenProvider;
import com.planetrush.planetrush.core.jwt.dto.JwtToken;
import com.planetrush.planetrush.member.service.dto.ReissueDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ReissueTokenServiceImpl implements ReissueTokenService {

	private final JwtTokenProvider jwtTokenProvider;

	@Value("${jwt.refresh-token.expiretime}")
	private int REFRESH_TOKEN_EXPIRATION_TIME;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReissueDto reissueToken(String refreshToken) {
		Long memberId = jwtTokenProvider.getMemberIdFromRefreshToken(refreshToken);
		jwtTokenProvider.deleteRefreshToken(refreshToken);

		JwtToken newToken = jwtTokenProvider.createToken(memberId);

		return ReissueDto.builder()
			.accessToken(newToken.getAccessToken())
			.refreshToken(newToken.getRefreshToken())
			.build();
	}
}


