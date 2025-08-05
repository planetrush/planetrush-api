package com.planetrush.planetrush.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.planetrush.planetrush.core.aop.annotation.RequireJwtToken;
import com.planetrush.planetrush.core.aop.member.MemberContext;
import com.planetrush.planetrush.core.template.response.BaseResponse;
import com.planetrush.planetrush.member.service.UpdateMemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UpdateMemberController extends MemberController {

	private final UpdateMemberService updateMemberService;

	/**
	 * 유저의 닉네임을 변경합니다.
	 * @param nickname 변경할 닉네임
	 * @return ResponseEntity
	 */
	@RequireJwtToken
	@PatchMapping("/profile")
	public ResponseEntity<BaseResponse<?>> updateMemberNickname(@RequestParam("nickname") String nickname) {
		Long memberId = MemberContext.getMemberId();
		updateMemberService.updateMemberNickname(memberId, nickname);
		return ResponseEntity.ok(BaseResponse.ofSuccess());
	}

}
