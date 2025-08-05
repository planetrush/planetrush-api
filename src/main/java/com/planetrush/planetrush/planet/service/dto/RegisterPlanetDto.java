package com.planetrush.planetrush.planet.service.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RegisterPlanetDto {

	private long id;
	private String name;
	private String content;
	private String category;
	private LocalDate startDate;
	private LocalDate endDate;
	private int maxParticipants;
	private String authCond;
	private Long memberId;
	private String planetImgUrl;
	private String standardVerificationImgUrl;

}
