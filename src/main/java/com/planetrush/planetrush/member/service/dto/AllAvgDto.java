package com.planetrush.planetrush.member.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AllAvgDto {

	private double totalAvg;
	private double exerciseAvg;
	private double beautyAvg;
	private double lifeAvg;
	private double studyAvg;
	private double etcAvg;

}
