package com.planetrush.planetrush.planet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.planetrush.planetrush.core.aop.annotation.RequireJwtToken;
import com.planetrush.planetrush.core.aop.member.MemberContext;
import com.planetrush.planetrush.core.template.response.BaseResponse;
import com.planetrush.planetrush.planet.controller.request.RegisterPlanetReq;
import com.planetrush.planetrush.planet.facade.RegisterPlanetFacade;
import com.planetrush.planetrush.planet.facade.dto.RegisterPlanetFacadeDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RegisterPlanetController extends PlanetController {

	private final RegisterPlanetFacade registerPlanetFacade;

	/**
	 * 행성을 생성합니다.
	 * @param customPlanetImg 기본 행성 이미지
	 * @param stdVerificationImg 인증 대표 이미지
	 * @param req 행성 내용
	 * @return ResponseEntity
	 */
	@RequireJwtToken
	@PostMapping
	public ResponseEntity<BaseResponse<?>> registerPlanet(
		@RequestPart(name = "customPlanetImg", required = false) MultipartFile customPlanetImg,
		@RequestPart(name = "stdVerificationImg") MultipartFile stdVerificationImg,
		@RequestPart(name = "req") RegisterPlanetReq req) {
		Long memberId = MemberContext.getMemberId();
		registerPlanetFacade.registerPlanet(RegisterPlanetFacadeDto.builder()
			.name(req.getName())
			.content(req.getContent())
			.category(req.getCategory())
			.startDate(req.getStartDate())
			.endDate(req.getEndDate())
			.maxParticipants(req.getMaxParticipants())
			.authCond(req.getAuthCond())
			.memberId(memberId)
			.planetImgUrl(req.getPlanetImgUrl())
			.build(), customPlanetImg, stdVerificationImg);
		return ResponseEntity.ok(BaseResponse.ofSuccess());
	}
}
