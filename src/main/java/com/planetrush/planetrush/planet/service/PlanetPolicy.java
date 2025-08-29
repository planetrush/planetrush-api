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

	/**
	 * 최대 입주 가능 인원을 확인합니다.
	 */
	public static void validateResidentLimit(Member member, ResidentRepositoryCustom residentRepositoryCustom) {
		if (residentRepositoryCustom.getReadyAndInProgressResidents(member) >= 9) {
			throw new ResidentOverflowException("resident count overflow");
		}
	}

	/**
	 * 동일한 행성에 중복 가입을 방지합니다.
	 */
	public static void validateDuplicateResident(Member member, Planet planet, ResidentRepository residentRepository) {
		residentRepository.findByMemberIdAndPlanetId(member.getId(), planet.getId())
			.ifPresent(resident -> {
				throw new ResidentAlreadyExistsException("resident already exists: " + resident.getId());
			});
	}

	public static void validateStartDateWithinTwoWeeks(LocalDate startDate) {
		if(ChronoUnit.DAYS.between(LocalDate.now(), startDate) > 14) {
			throw new InvalidStartDateException("Start date must be within 14 days from today.");
		}
	}

}
