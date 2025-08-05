package com.planetrush.planetrush.planet.service.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PlanetSubscriptionDto {
	private long memberId;
	private long planetId;
}
