package com.planetrush.planetrush.planet.service;

import com.planetrush.planetrush.planet.service.dto.RegisterPlanetDto;

public interface RegisterPlanetService {

	/**
	 * 행성을 등록합니다.
	 * @param dto 행성 정보
	 */
	void registerPlanet(RegisterPlanetDto dto);

}
