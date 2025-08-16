package com.planetrush.planetrush.fixture;

import com.planetrush.planetrush.member.domain.Member;
import com.planetrush.planetrush.planet.domain.Planet;
import com.planetrush.planetrush.planet.domain.Resident;

public final class ResidentFixture {

	public static Resident creator(Member member, Planet planet) {
		return Resident.isCreator(member, planet);
	}

	public static Resident notCreator(Member member, Planet planet) {
		return Resident.isNotCreator(member, planet);
	}
}
