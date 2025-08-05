package com.planetrush.planetrush.member.repository.custom;

import static com.planetrush.planetrush.member.domain.QChallengeHistory.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.planetrush.planetrush.member.domain.ChallengeHistory;
import com.planetrush.planetrush.member.domain.ChallengeResult;
import com.planetrush.planetrush.member.service.dto.CollectionSearchCond;
import com.planetrush.planetrush.planet.domain.Category;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ChallengeHistoryRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	/**
	 * 회원별 챌린지 진행률의 평균을 계산합니다.
	 *
	 * @return 회원 id를 기준으로 카테고리별 평균 진행률
	 */
	public Map<Long, Map<Category, Double>> getAverageScoreByMember() {

		List<Tuple> result = queryFactory
			.select(
				challengeHistory.member.id,
				challengeHistory.category,
				challengeHistory.progress.avg()
			)
			.from(challengeHistory)
			.groupBy(challengeHistory.member.id, challengeHistory.category)
			.fetch();
		return result.stream()
			.collect(Collectors.groupingBy(
				tuple -> tuple.get(challengeHistory.member.id),
				Collectors.toMap(
					tuple -> tuple.get(challengeHistory.category),
					tuple -> tuple.get(challengeHistory.progress.avg())
				)
			));
	}

	/**
	 * 회원별 챌린지 기록을 페이징 처리하여 조회합니다.
	 *
	 * @param cond 검색 조건 (memberId, lastId, size)
	 * @return 페이징 처리된 챌린지 기록 목록
	 */
	public List<ChallengeHistory> getMyChallengeHistory(CollectionSearchCond cond) {
		return queryFactory
			.selectFrom(challengeHistory)
			.where(
				memberIdEq(cond.getMemberId()),
				ltLastHistoryId(cond.getLastHistoryId()),
				resultIsSuccess()
			)
			.orderBy(challengeHistory.id.desc())
			.limit(cond.getSize() + 1)
			.fetch();
	}

	private BooleanExpression memberIdEq(Long memberId) {
		return memberId != null ? challengeHistory.member.id.eq(memberId) : null;
	}

	private BooleanExpression ltLastHistoryId(Long lastHistoryId) {
		return lastHistoryId != null ? challengeHistory.id.lt(lastHistoryId) : null;
	}

	private BooleanExpression resultIsSuccess() {
		return challengeHistory.result.eq(ChallengeResult.SUCCESS);
	}
}
