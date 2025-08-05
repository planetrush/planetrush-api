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
import com.planetrush.planetrush.verification.repository.VerificationRecordRepository;
import com.planetrush.planetrush.verification.service.dto.VerificationResultDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class VerificationServiceImpl implements VerificationService {

	private final MemberRepository memberRepository;
	private final PlanetRepository planetRepository;
	private final VerificationRecordRepository verificationRecordRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getStandardImgUrlByPlanetId(Long planetId) {
		Planet planet = planetRepository.findById(planetId)
			.orElseThrow(() -> new PlanetNotFoundException(("Planet not found with ID: " + planetId)));
		return planet.getStandardVerificationImg();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveVerificationResult(VerificationResultDto dto) {
		Member member = memberRepository.findById(dto.getMemberId())
			.orElseThrow(() -> new MemberNotFoundException("Member not found with ID: " + dto.getMemberId()));
		Planet planet = planetRepository.findById(dto.getPlanetId())
			.orElseThrow(() -> new PlanetNotFoundException("Planet not found with ID: " + dto.getPlanetId()));
		verificationRecordRepository.save(VerificationRecord.builder()
			.verified(dto.isVerified())
			.planet(planet)
			.member(member)
			.similarityScore(dto.getSimilarityScore())
			.imgUrl(dto.getImgUrl())
			.build());
	}

}
