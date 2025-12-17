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
import com.planetrush.planetrush.planet.service.vo.GetMainPlanetListVo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PlanetValidator {

	private final ResidentRepository residentRepository;
	private final ResidentRepositoryCustom residentRepositoryCustom;
	private static final int MAX_RESIDENT_LIMIT = 9;
	private static final int MAX_CHALLENGE_START_OFFSET = 14;

	private static final int LAST_DAY_FROM_PLANET_START_DAYS = 2;
	private static final int LAST_DAY_AFTER_VERIFY_DAYS = 3;

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

	/**
	 * 마지막 인증 날짜 혹은 행성 시작 날짜와 오늘 날짜를 비교해 결과를 반환합니다.
	 * @param vo GetMainPlanetListVo
	 * @return 행성에서 탈퇴 당하기 직전인지 여부
	 */
	public boolean checkLastDay(GetMainPlanetListVo vo) {
		LocalDate today = LocalDate.now();
		if (vo.getLastVerifyDate() != null) {
			return isLastDayAfterVerify(vo.getLastVerifyDate().toLocalDate(), today);
		}
		return isLastDayFromPlanetStart(vo.getPlanetStartDate(), today);
	}

	private boolean isLastDayFromPlanetStart(LocalDate planetStartDate, LocalDate today) {
		long daysPassed = ChronoUnit.DAYS.between(planetStartDate, today);
		return daysPassed >= LAST_DAY_FROM_PLANET_START_DAYS;
	}

	private boolean isLastDayAfterVerify(LocalDate lastVerifyDate, LocalDate today) {
		long daysPassed = ChronoUnit.DAYS.between(lastVerifyDate, today);
		return daysPassed >= LAST_DAY_AFTER_VERIFY_DAYS;
	}
}
