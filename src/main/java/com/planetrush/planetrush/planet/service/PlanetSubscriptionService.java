package com.planetrush.planetrush.planet.service;

import com.planetrush.planetrush.planet.service.dto.PlanetSubscriptionDto;

public interface PlanetSubscriptionService {

	/**
	 * 주어진 dto를 사용하여 입주 기록을 생성합니다.
	 * @param dto 사용자의 id, 행성의 id 등을 포함합니다.
	 */
	void registerResident(PlanetSubscriptionDto dto);

	/**
	 * 주어진 dto를 사용하여 입주 기록을 삭제합니다.
	 * @param dto 사용자의 id, 행성의 id 등을 포함합니다.
	 */
	void deleteResident(PlanetSubscriptionDto dto);
}
