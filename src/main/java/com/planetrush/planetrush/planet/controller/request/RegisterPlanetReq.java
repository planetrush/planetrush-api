package com.planetrush.planetrush.planet.controller.request;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterPlanetReq {

	private String name;
	private String content;
	private String category;
	private LocalDate startDate;
	private LocalDate endDate;
	private int maxParticipants;
	private String authCond;
	private String planetImgUrl;

}
