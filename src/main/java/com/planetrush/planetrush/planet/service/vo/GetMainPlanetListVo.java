package com.planetrush.planetrush.planet.service.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetMainPlanetListVo {

	private Long planetId;
	private String planetImgUrl;
	private String name;
	private String status;
	private LocalDate planetStartDate;
	private LocalDateTime lastVerifyDate;

}
