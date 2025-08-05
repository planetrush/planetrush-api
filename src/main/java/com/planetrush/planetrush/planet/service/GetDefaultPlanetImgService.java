package com.planetrush.planetrush.planet.service;

import java.util.List;

import com.planetrush.planetrush.planet.service.dto.GetDefaultPlanetImgDto;

public interface GetDefaultPlanetImgService {

	/**
	 * 모든 기본 행성 이미지 URL을 조회합니다.
	 * @return 모든 기본 행성 이미지의 URL 목록을 가져와 DefaultPlanetImgDto로 반환합니다.
	 * @see GetDefaultPlanetImgDto
	 */
	List<GetDefaultPlanetImgDto> getAllImgUrls();

}
