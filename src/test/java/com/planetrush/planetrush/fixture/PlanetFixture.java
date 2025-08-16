package com.planetrush.planetrush.fixture;

import java.time.LocalDate;

import com.planetrush.planetrush.planet.domain.Category;
import com.planetrush.planetrush.planet.domain.Planet;

public final class PlanetFixture {

	private static final String PLANET_IMG = "https://planet-img.com";
	private static final String STANDARD_VERIFICATION__IMG = "https://standard-verification-img.com";
	private static final String NAME = "planetName";
	private static final String CONTENT = "planetContent";
	private static final String VERIFICATION_COND = "verificationCond";

	public static Planet readyPlanet() {
		return readyPlanet(
			5,
			LocalDate.now().plusDays(5),
			LocalDate.now().plusDays(10),
			Category.ETC
		);
	}

	public static Planet readyPlanet(int maxParticipants, LocalDate startDate, LocalDate endDate, Category category) {
		return Planet.builder()
			.planetImg(PLANET_IMG)
			.name(NAME)
			.content(CONTENT)
			.verificationCond(VERIFICATION_COND)
			.standardVerificationImg(STANDARD_VERIFICATION__IMG)
			.category(category)
			.maxParticipants(maxParticipants)
			.startDate(startDate)
			.endDate(endDate)
			.build();
	}
}
