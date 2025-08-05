package com.planetrush.planetrush.member.repository.custom;

import static com.planetrush.planetrush.member.domain.QProgressAvg.*;

import org.springframework.stereotype.Repository;

import com.planetrush.planetrush.member.domain.ProgressAvg;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ProgressAvgRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public void updateProgressAvg(Long memberId, ProgressAvg newProgressAvg) {
		queryFactory.update(progressAvg)
			.set(progressAvg.beautyAvg, newProgressAvg.getBeautyAvg())
			.set(progressAvg.exerciseAvg, newProgressAvg.getExerciseAvg())
			.set(progressAvg.lifeAvg, newProgressAvg.getLifeAvg())
			.set(progressAvg.studyAvg, newProgressAvg.getStudyAvg())
			.set(progressAvg.etcAvg, newProgressAvg.getEtcAvg())
			.set(progressAvg.totalAvg, newProgressAvg.getTotalAvg())
			.where(progressAvg.member.id.eq(memberId))
			.execute();
	}

}
