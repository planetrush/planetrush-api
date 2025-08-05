package com.planetrush.planetrush.planet.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetDefaultPlanetImgDto {

	private Long imgId;
	private String imgUrl;

}
