package com.planetrush.planetrush.planet.service.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class OngoingPlanetDto {

	private Long planetId;
	private String planetImg;
	private String standardVerificationImg;
	private String category;
	private String name;
	private String content;
	private LocalDate startDate;
	private LocalDate endDate;
	private long totalVerificationCnt;
	private List<ResidentDto> residents;
	private boolean isVerifiedToday;

}
