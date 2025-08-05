package com.planetrush.planetrush.planet.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SearchCond {

	private String keyword;
	private String category;
	private int size;
	private Long lastPlanetId;

}
