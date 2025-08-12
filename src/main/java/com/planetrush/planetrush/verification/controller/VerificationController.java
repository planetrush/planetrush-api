package com.planetrush.planetrush.verification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planetrush.planetrush.core.aop.annotation.RequireJwtToken;
import com.planetrush.planetrush.core.aop.member.MemberContext;
import com.planetrush.planetrush.core.template.response.BaseResponse;
import com.planetrush.planetrush.verification.controller.req.VerificationReq;
import com.planetrush.planetrush.verification.service.VerificationService;
import com.planetrush.planetrush.verification.service.dto.VerificationDto;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class VerificationController {

	private final VerificationService verificationService;

	/**
	 * 챌린지 인증
	 * @param verificationImg 인증 사진
	 * @param planetId 행성의 고유 id
	 * @return 인증 여부 및 유사도를 담은 ResponseEntity
	 */
	@RequireJwtToken
	@PostMapping("/verify/planets/{planet-id}")
	public ResponseEntity<BaseResponse<?>> verifyChallenge(
		@PathVariable("planet-id") Long planetId,
		@RequestBody VerificationReq req) {
		Long memberId = MemberContext.getMemberId();
		verificationService.verifyTodayChallenge(VerificationDto.builder()
			.memberId(memberId)
			.planetId(planetId)
			.verificationImgUrl(req.getVerificationImgUrl())
			.build());
		return ResponseEntity.ok(BaseResponse.ofSuccess());
	}

}
