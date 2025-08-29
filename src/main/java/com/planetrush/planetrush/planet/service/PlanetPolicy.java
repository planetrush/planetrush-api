package com.planetrush.planetrush.planet.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

import com.planetrush.planetrush.member.domain.Member;
import com.planetrush.planetrush.planet.domain.Planet;
import com.planetrush.planetrush.planet.exception.InvalidStartDateException;
import com.planetrush.planetrush.planet.exception.ResidentAlreadyExistsException;
import com.planetrush.planetrush.planet.exception.ResidentOverflowException;
import com.planetrush.planetrush.planet.repository.ResidentRepository;
import com.planetrush.planetrush.planet.repository.custom.ResidentRepositoryCustom;

@Component
public final class PlanetPolicy {

	private static final int MAX_RESIDENT_LIMIT = 9;
	private static final int MAX_CHALLENGE_START_OFFSET = 14;

	/**
	 * 가입 가능한 최대 챌린지 수를 초과하는지 검사합니다.
	 * @param member
	 * @param residentRepositoryCustom
	 */
	public static void validateResidentLimit(Member member, ResidentRepositoryCustom residentRepositoryCustom) {
		if (residentRepositoryCustom.getReadyAndInProgressResidents(member) >= MAX_RESIDENT_LIMIT) {
			throw new ResidentOverflowException("resident count overflow");
		}
	}

	/**
	 * 동일한 행성에 중복 가입을 방지합니다.
	 * @param member
	 * @param planet
	 * @param residentRepository
	 */
	public static void validateDuplicateResident(Member member, Planet planet, ResidentRepository residentRepository) {
		residentRepository.findByMemberIdAndPlanetId(member.getId(), planet.getId())
			.ifPresent(resident -> {
				throw new ResidentAlreadyExistsException("resident already exists: " + resident.getId());
			});
	}

	/**
	 * 2주 이내로 시작하는지 검사합니다.
	 * @param startDate
	 */
	public static void validateStartDateWithinTwoWeeks(LocalDate startDate) {
		if(ChronoUnit.DAYS.between(LocalDate.now(), startDate) > MAX_CHALLENGE_START_OFFSET) {
			throw new InvalidStartDateException("Start date must be within 14 days from today.");
		}
	}

}
