package com.planetrush.planetrush.planet.service.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetMyPlanetListDto {

	private Long planetId;
	private String planetImg;
	private String category;
	private String name;
	private String content;
	private LocalDate startDate;
	private LocalDate endDate;
	private int currentParticipants;
	private int maxParticipants;
	private String status;

}
