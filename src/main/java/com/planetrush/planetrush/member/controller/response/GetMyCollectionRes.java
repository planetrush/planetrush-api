package com.planetrush.planetrush.member.controller.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.planetrush.planetrush.member.service.dto.PlanetCollectionDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetMyCollectionRes {

	private List<PlanetCollectionDto> planetCollection;
	@JsonProperty(value = "hasNext")
	private boolean hasNext;
}
