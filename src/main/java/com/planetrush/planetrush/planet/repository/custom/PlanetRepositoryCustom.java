package com.planetrush.planetrush.planet.repository.custom;

import static com.planetrush.planetrush.planet.domain.QPlanet.*;
import static com.planetrush.planetrush.planet.domain.QResident.*;
import static com.planetrush.planetrush.verification.domain.QVerificationRecord.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.planetrush.planetrush.member.domain.Member;
import com.planetrush.planetrush.planet.domain.Planet;
import com.planetrush.planetrush.planet.domain.PlanetStatus;
import com.planetrush.planetrush.planet.service.dto.GetMyPlanetListDto;
import com.planetrush.planetrush.planet.service.dto.SearchCond;
import com.planetrush.planetrush.planet.service.vo.GetMainPlanetListVo;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class PlanetRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	/**
	 * 행성을 검색하는 커스텀 쿼리 메서드입니다.
	 * @param cond 검색 조건
	 * @return 검색 결과
	 */
	public List<Planet> searchPlanet(SearchCond cond) {
		return queryFactory.selectFrom(planet)
			.where(
				isReadyStatus(),
				isKeywordContained(cond.getKeyword()),
				ltPlanetId(cond.getLastPlanetId()),
				isInCategory(cond.getCategory()))
			.orderBy(planet.id.desc())
			.limit(cond.getSize() + 1)
			.fetch();
	}

	/**
	 * 마이페이지를 위한 참여중이면서 진행 전, 진행 중인 행성을 반환합니다.
	 * @param member 유저 객체
	 * @return 행성 목록
	 */
	public List<GetMyPlanetListDto> getMyPlanetList(Member member) {
		return queryFactory.select(Projections.constructor(GetMyPlanetListDto.class,
				planet.id,
				planet.planetImg,
				planet.category.stringValue(),
				planet.name,
				planet.content,
				planet.startDate,
				planet.endDate,
				planet.currentParticipants,
				planet.maxParticipants,
				planet.status.stringValue()
			))
			.from(resident)
			.where(
				resident.planet.status.in(PlanetStatus.READY, PlanetStatus.IN_PROGRESS),
				resident.member.eq(member),
				resident.banned.isFalse()
			)
			.fetch();
	}

	/**
	 * 메인페이지를 위한 참여중이면서 진행 전, 진행 중인 행성을 반환합니다.
	 * @param member 유저 객체
	 * @return 행성 목록
	 */
	public List<GetMainPlanetListVo> getMainPlanetList(Member member) {
		return queryFactory.select(Projections.constructor(GetMainPlanetListVo.class,
				planet.id,
				planet.planetImg,
				planet.name,
				planet.status.stringValue(),
				planet.startDate,
				JPAExpressions.select(verificationRecord.uploadDate.max())
					.from(verificationRecord)
					.where(verificationRecord.planet.eq(planet)
						.and(verificationRecord.member.eq(member))
						.and(verificationRecord.verified.isTrue()))
			))
			.from(resident)
			.join(resident.planet, planet)
			.where(
				planet.status.in(PlanetStatus.READY, PlanetStatus.IN_PROGRESS),
				resident.member.eq(member),
				resident.banned.isFalse()
			)
			.fetch();
	}

	/**
	 * planet의 상태를 READY에서 IN_PROGRESS로 업데이트합니다.
	 *
	 * planet의 상태가 READY이고 시작일자가 오늘인 경우에 해당합니다.
	 */
	public void updateStatusReadyToInProgress() {
		queryFactory.update(planet)
			.set(planet.status, PlanetStatus.IN_PROGRESS)
			.where(
				isReadyStatus(),
				eqStartDateAndToday()
			)
			.execute();
	}

	/**
	 * planet의 상태를 IN_PROGRESS에서 UNDER_REVIEW로 업데이트합니다.
	 *
	 * planet의 상태가 IN_PROGRESS이고 종료일자가 오늘인 경우에 해당합니다.
	 */
	public void updateStatusInProgressToUnderReview() {
		queryFactory.update(planet)
			.set(planet.status, PlanetStatus.UNDER_REVIEW)
			.where(
				isInProgressStatus(),
				eqEndDateAndToday()
			)
			.execute();
	}

	/**
	 * 상태가 IN_PROGRESS 또는 UNDER_REVIEW인 모든 planet을 조회합니다.
	 *
	 * @return 상태가 IN_PROGRESS 또는 UNDER_REVIEW인 planet의 리스트
	 */
	public List<Planet> findAllStatusIsInProgressAndUnderReview() {
		return queryFactory.selectFrom(planet)
			.where(isInProgressStatus().or(isUnderReviewStatus()))
			.fetch();
	}

	/**
	 * 상태가 UNDER_REVIEW인 모든 planet을 조회합니다.
	 *
	 * @return 상태가 UNDER_REVIEW인 planet의 리스트
	 */
	public List<Planet> findAllStatusIsUnderReview() {
		return queryFactory.selectFrom(planet)
			.where(
				isUnderReviewStatus()
			)
			.fetch();
	}

	private BooleanExpression isReadyStatus() {
		return planet.status.eq(PlanetStatus.READY);
	}

	private BooleanExpression isInProgressStatus() {
		return planet.status.eq(PlanetStatus.IN_PROGRESS);
	}

	private BooleanExpression isUnderReviewStatus() {
		return planet.status.eq(PlanetStatus.UNDER_REVIEW);
	}

	private BooleanExpression eqStartDateAndToday() {
		return planet.startDate.eq(LocalDate.now());
	}

	private BooleanExpression eqEndDateAndToday() {
		return planet.endDate.eq(LocalDate.now());
	}

	private BooleanExpression isKeywordContained(String keyword) {
		return keyword != null ? planet.content.contains(keyword) : null;
	}

	private BooleanExpression ltPlanetId(Long lastPlanetId) {
		return lastPlanetId != null ? planet.id.lt(lastPlanetId) : null;
	}

	private BooleanExpression isInCategory(String category) {
		return category != null ? planet.category.stringValue().eq(category) : null;
	}
}
