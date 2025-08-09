package com.planetrush.planetrush.infra.flask.res;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class FlaskResponseDto {

	@JsonProperty("similarity_score")
	private double similarityScore;
	private boolean verified;
}
