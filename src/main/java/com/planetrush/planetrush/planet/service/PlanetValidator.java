package com.planetrush.planetrush.planet.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.planetrush.planetrush.member.domain.Member;
import com.planetrush.planetrush.planet.domain.Planet;
import com.planetrush.planetrush.planet.exception.InvalidStartDateException;
import com.planetrush.planetrush.planet.exception.ResidentAlreadyExistsException;
import com.planetrush.planetrush.planet.exception.ResidentOverflowException;
import com.planetrush.planetrush.planet.repository.ResidentRepository;
import com.planetrush.planetrush.planet.repository.custom.ResidentRepositoryCustom;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PlanetValidator {

	private final ResidentRepository residentRepository;
	private final ResidentRepositoryCustom residentRepositoryCustom;
	private static final int MAX_RESIDENT_LIMIT = 9;
	private static final int MAX_CHALLENGE_START_OFFSET = 14;

	/**
	 * 가입 가능한 최대 챌린지 수를 초과하는지 검사합니다.
	 * @param member
	 */
	@Transactional(readOnly = true)
	public void checkMaxResidentLimit(Member member) {
		if (residentRepositoryCustom.getReadyAndInProgressResidents(member) >= MAX_RESIDENT_LIMIT) {
			throw new ResidentOverflowException("resident count overflow");
		}
	}

	/**
	 * 동일한 행성에 중복 가입을 방지합니다.
	 * @param member
	 * @param planet
	 */
	@Transactional(readOnly = true)
	public void checkDuplicatedRegister(Member member, Planet planet) {
		residentRepository.findByMemberIdAndPlanetId(member.getId(), planet.getId())
			.ifPresent(resident -> {
				throw new ResidentAlreadyExistsException("resident already exists: " + resident.getId());
			});
	}

	/**
	 * 2주 이내로 시작하는지 검사합니다.
	 * @param startDate
	 */
	public void checkStartDate(LocalDate startDate) {
		if (LocalDate.now().minus(MAX_CHALLENGE_START_OFFSET, ChronoUnit.DAYS).isAfter(startDate)) {
			throw new InvalidStartDateException("Start date must be within 14 days from today.");
		}
	}

}
