package com.planetrush.planetrush.planet.repository.custom;

import static com.planetrush.planetrush.planet.domain.QPlanet.*;
import static com.planetrush.planetrush.planet.domain.QResident.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.planetrush.planetrush.member.domain.Member;
import com.planetrush.planetrush.planet.domain.Planet;
import com.planetrush.planetrush.planet.domain.Resident;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ResidentRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	/**
	 * 유저가 행성에 참여한 상태인지 확인합니다.
	 * @param member 유저 객체
	 * @param planet 행성 객체
	 * @return 참여 여부
	 */
	public boolean isResidentOfPlanet(Member member, Planet planet) {
		return queryFactory.selectFrom(resident)
			.where(
				eqMember(member),
				resident.planet.eq(planet)
			)
			.fetchOne() != null;
	}

	/**
	 * 현재 참여중이며 퇴출 당하지 않은 기록을 확인합니다.
	 * @param planet 행성 객체
	 * @return 참여 기록
	 */
	public List<Resident> getResidentsNotBanned(Planet planet) {
		return queryFactory.selectFrom(resident)
			.where(
				resident.planet.eq(planet),
				isNotBanned()
			)
			.fetch();
	}

	/**
	 * 현재 준비 상태 또는 진행 중인 상태에 참여 중인 행성의 수를 확인합니다.
	 * @param member 유저 객체
	 * @return 행성의 수
	 */
	public int getReadyAndInProgressResidents(Member member) {
		return queryFactory
			.selectFrom(resident)
			.join(resident.planet, planet)
			.where(
				planet.status.stringValue().in("READY", "IN_PROGRESS"),
				resident.member.eq(member),
				resident.banned.isFalse()
			)
			.fetch().size();
	}

	/**
	 * 챌린지가 진행중인 행성의 참가자 중, 마지막 인증이 3일 전인 경우 해당 회원은 제거됩니다.
	 */
	public void banMemberFromPlanet(Member member, Planet planet) {
		queryFactory.update(resident)
			.set(resident.banned, true)
			.where(resident.member.eq(member),
				resident.planet.eq(planet))
			.execute();
	}

	private BooleanExpression eqMember(Member member) {
		return resident.member.eq(member);
	}

	private BooleanExpression eqPlanet(Planet planet) {
		return resident.planet.eq(planet);
	}
	
	private BooleanExpression isNotBanned() {
		return resident.banned.isFalse();
	}

}
