package com.planetrush.planetrush.member.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CategoryPerDto {

	private double myTotalPer;
	private double myExercisePer;
	private double myBeautyPer;
	private double myLifePer;
	private double myStudyPer;
	private double myEtcPer;

}
