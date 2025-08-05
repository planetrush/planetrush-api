package com.planetrush.planetrush.verification.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planetrush.planetrush.member.domain.Member;
import com.planetrush.planetrush.member.exception.MemberNotFoundException;
import com.planetrush.planetrush.member.repository.MemberRepository;
import com.planetrush.planetrush.planet.domain.Planet;
import com.planetrush.planetrush.planet.exception.PlanetNotFoundException;
import com.planetrush.planetrush.planet.repository.PlanetRepository;
import com.planetrush.planetrush.verification.domain.VerificationRecord;
import com.planetrush.planetrush.verification.repository.custom.VerificationRecordRepositoryCustom;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class GetTodayRecordServiceImpl implements GetTodayRecordService {

	private final MemberRepository memberRepository;
	private final PlanetRepository planetRepository;
	private final VerificationRecordRepositoryCustom verificationRecordRepositoryCustom;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean getTodayRecord(Long memberId, Long planetId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException("Member not found with ID: " + memberId));
		Planet planet = planetRepository.findById(planetId)
			.orElseThrow(() -> new PlanetNotFoundException("Planet not found with ID: " + planetId));
		VerificationRecord verificationRecord = verificationRecordRepositoryCustom.findTodayRecord(member, planet);
		return verificationRecord != null;
	}
}
