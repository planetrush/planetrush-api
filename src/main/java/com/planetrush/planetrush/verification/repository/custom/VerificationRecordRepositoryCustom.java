package com.planetrush.planetrush.verification.repository.custom;

import static com.planetrush.planetrush.verification.domain.QVerificationRecord.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.planetrush.planetrush.member.domain.Member;
import com.planetrush.planetrush.planet.domain.Planet;
import com.planetrush.planetrush.verification.domain.VerificationRecord;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class VerificationRecordRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	/**
	 * 인증 성공한 데이터를 불러옵니다.
	 *
	 * @param member 유저의 고유 id
	 * @param planet 행성의 고유 id
	 * @return 인증 성공한 기록
	 */
	public List<VerificationRecord> findVerificationRecordsByMemberIdAndPlanetId(Member member, Planet planet) {
		return queryFactory.selectFrom(verificationRecord)
			.where(
				eqMember(member),
				eqPlanet(planet),
				isVerified()
			)
			.fetch();
	}

	/**
	 * 행성의 성공 인증 기록 중 제일 최신 데이터를 조회합니다.
	 *
	 * @param member 회원 엔티티
	 * @param planet 행성 엔티티
	 * @return 가장 최신 데이터 or null
	 */
	public VerificationRecord findLatestRecord(Member member, Planet planet) {
		return queryFactory.selectFrom(verificationRecord)
			.where(
				eqMember(member),
				eqPlanet(planet),
				isVerified())
			.orderBy(verificationRecord.uploadDate.desc())
			.fetchFirst();
	}

	/**
	 * 오늘 인증을 하였는지 여부를 조회합니다.
	 * @param member 회원 엔티티
	 * @param planet 행성 엔티티
	 * @return 오늘 데이터 or null
	 */
	public VerificationRecord findTodayRecord(Member member, Planet planet) {
		LocalDate today = LocalDate.now();
		LocalDateTime startOfToday = today.atStartOfDay();
		LocalDateTime endOfToday = today.atTime(LocalTime.MAX);
		return queryFactory.selectFrom(verificationRecord)
			.where(
				eqMember(member),
				eqPlanet(planet),
				isVerified(),
				uploadDateBetween(startOfToday, endOfToday))
			.fetchFirst();
	}

	/**
	 * 행성의 성공 인증 기록 중 제일 최신 데이터를 조회합니다.
	 *
	 * @param member 회원 엔티티
	 * @param planet 행성 엔티티
	 * @return 인증 성공한 기록
	 */
	public List<VerificationRecord> findAllByMemberAndPlanet(Member member, Planet planet) {
		return queryFactory.selectFrom(verificationRecord)
			.where(
				eqMember(member),
				eqPlanet(planet),
				isVerified()
			)
			.fetch();
	}

	private BooleanExpression isVerified() {
		return verificationRecord.verified.isTrue();
	}

	private BooleanExpression eqMember(Member member) {
		return verificationRecord.member.eq(member);
	}

	private BooleanExpression eqPlanet(Planet planet) {
		return verificationRecord.planet.eq(planet);
	}

	private BooleanExpression uploadDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay) {
		return verificationRecord.uploadDate.between(startOfDay, endOfDay);
	}
}

