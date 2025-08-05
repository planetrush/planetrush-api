package com.planetrush.planetrush.planet.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetMainPlanetListDto {

	private Long planetId;
	private String planetImgUrl;
	private String name;
	private String status;
	@JsonProperty(value = "isLastDay")
	private boolean lastDay;

}
