package com.planetrush.planetrush.member.service.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetMyProgressAvgDto {

	private long completionCnt;
	private long challengeCnt;
	private double myTotalAvg;
	private double myTotalPer;
	private double totalAvg;
	private double myExerciseAvg;
	private double myExercisePer;
	private double exerciseAvg;
	private double myBeautyAvg;
	private double myBeautyPer;
	private double beautyAvg;
	private double myLifeAvg;
	private double myLifePer;
	private double lifeAvg;
	private double myStudyAvg;
	private double myStudyPer;
	private double studyAvg;
	private double myEtcAvg;
	private double myEtcPer;
	private double etcAvg;

}
