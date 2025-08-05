package com.planetrush.planetrush.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planetrush.planetrush.member.domain.Member;
import com.planetrush.planetrush.member.exception.MemberNotFoundException;
import com.planetrush.planetrush.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UpdateMemberServiceImpl implements UpdateMemberService {

	private final MemberRepository memberRepository;

	/**
	 * {@inheritDoc}
	 *
	 * @throws MemberNotFoundException 유저를 찾을 수 없을 때 발생
	 */
	@Override
	public void updateMemberNickname(Long memberId, String nickname) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException("Member not found with ID: " + memberId));
		member.updateNickname(nickname);
	}

}
