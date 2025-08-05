package com.planetrush.planetrush.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.planetrush.planetrush.core.template.response.BaseResponse;
import com.planetrush.planetrush.member.controller.request.ReissueReq;
import com.planetrush.planetrush.member.service.ReissueTokenService;
import com.planetrush.planetrush.member.service.dto.ReissueDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ReissueTokenController extends MemberController {

	private final ReissueTokenService reissueTokenService;

	/**
	 * 사용자의 리프레시 토큰을 받아 새로운 액세스 토큰을 발급합니다.
	 * @param req 리프레시 토큰을 담고 있는 요청 객체 (ReissueReq)
	 * @return 새로운 액세스 토큰이 포함된 ReissueDto 객체를 감싸는 ResponseEntity 객체
	 */
	@PostMapping("/auth/reissue")
	public ResponseEntity<BaseResponse<ReissueDto>> reissueToken(@RequestBody ReissueReq req) {
		ReissueDto newToken = reissueTokenService.reissueToken(req.getRefreshToken());
		return ResponseEntity.ok(BaseResponse.ofSuccess(newToken));
	}
}
