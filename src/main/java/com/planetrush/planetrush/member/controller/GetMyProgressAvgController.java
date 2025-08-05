package com.planetrush.planetrush.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planetrush.planetrush.core.aop.annotation.RequireJwtToken;
import com.planetrush.planetrush.core.aop.member.MemberContext;
import com.planetrush.planetrush.core.template.response.BaseResponse;
import com.planetrush.planetrush.member.service.GetMyProgressAvgService;
import com.planetrush.planetrush.member.service.dto.GetMyProgressAvgDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class GetMyProgressAvgController extends MemberController {

	private final GetMyProgressAvgService getMyProgressAvgService;

	/**
	 * 마이페이지를 위한 사용자의 통계 정보를 가져옵니다.
	 * @return ResponseEntity
	 */
	@RequireJwtToken
	@GetMapping("/mypage")
	public ResponseEntity<BaseResponse<GetMyProgressAvgDto>> getMyProgressAvg() {
		Long memberId = MemberContext.getMemberId();
		GetMyProgressAvgDto getMyProgressAvgDto = getMyProgressAvgService.getMyProgressAvgPer(memberId);
		return ResponseEntity.ok(BaseResponse.ofSuccess(getMyProgressAvgDto));
	}

}
