package com.planetrush.planetrush.verification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.planetrush.planetrush.core.aop.annotation.RequireJwtToken;
import com.planetrush.planetrush.core.aop.member.MemberContext;
import com.planetrush.planetrush.core.template.response.BaseResponse;
import com.planetrush.planetrush.verification.facade.VerificationFacade;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class VerificationController {

	private final VerificationFacade verificationFacade;

	/**
	 * 챌린지 인증
	 * @param verificationImg 인증 사진
	 * @param planetId 행성의 고유 id
	 * @return 인증 여부 및 유사도를 담은 ResponseEntity
	 */
	@RequireJwtToken
	@PostMapping("/verify/planets/{planet-id}")
	public ResponseEntity<BaseResponse<?>> verifyChallenge(
		@RequestParam("verificationImg") MultipartFile verificationImg,
		@PathVariable("planet-id") Long planetId
	) {
		Long memberId = MemberContext.getMemberId();
		verificationFacade.saveImgAndVerifyChallenge(verificationImg, memberId, planetId);
		return ResponseEntity.ok(BaseResponse.ofSuccess());
	}

}
