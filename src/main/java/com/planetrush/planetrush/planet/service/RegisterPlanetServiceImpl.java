package com.planetrush.planetrush.planet.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planetrush.planetrush.member.domain.Member;
import com.planetrush.planetrush.member.exception.MemberNotFoundException;
import com.planetrush.planetrush.member.repository.MemberRepository;
import com.planetrush.planetrush.planet.domain.Category;
import com.planetrush.planetrush.planet.domain.Planet;
import com.planetrush.planetrush.planet.domain.Resident;
import com.planetrush.planetrush.planet.repository.PlanetRepository;
import com.planetrush.planetrush.planet.repository.ResidentRepository;
import com.planetrush.planetrush.planet.service.dto.RegisterPlanetDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class RegisterPlanetServiceImpl implements RegisterPlanetService {

	private final PlanetRepository planetRepository;
	private final ResidentRepository residentRepository;
	private final MemberRepository memberRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerPlanet(RegisterPlanetDto dto) {
		Planet planet = planetRepository.save(Planet.builder()
			.name(dto.getName())
			.category(Category.valueOf(dto.getCategory()))
			.content(dto.getContent())
			.startDate(dto.getStartDate())
			.endDate(dto.getEndDate())
			.maxParticipants(dto.getMaxParticipants())
			.verificationCond(dto.getAuthCond())
			.planetImg(dto.getPlanetImgUrl())
			.standardVerificationImg(dto.getStandardVerificationImgUrl())
			.build());
		Member member = memberRepository.findById(dto.getMemberId())
			.orElseThrow(() -> new MemberNotFoundException("Member not found with ID: " + dto.getMemberId()));
		residentRepository.save(Resident.isCreator(member, planet));
	}

}
