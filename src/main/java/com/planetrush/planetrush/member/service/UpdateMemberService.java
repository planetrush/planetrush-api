package com.planetrush.planetrush.member.service;

public interface UpdateMemberService {

	/**
	 * 유저의 닉네임을 변경합니다.
	 * @param memberId 유저의 고유 id
	 * @param nickname 변경할 닉네임
	 */
	void updateMemberNickname(Long memberId, String nickname);
}
