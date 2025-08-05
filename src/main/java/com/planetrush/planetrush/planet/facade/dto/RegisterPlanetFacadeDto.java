package com.planetrush.planetrush.planet.facade.dto;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterPlanetFacadeDto {

	private String name;
	private String content;
	private String category;
	private LocalDate startDate;
	private LocalDate endDate;
	private int maxParticipants;
	private String authCond;
	private Long memberId;
	private String planetImgUrl;

}