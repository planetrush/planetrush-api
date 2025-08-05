package com.planetrush.planetrush.recommend.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class GetPopularKeywordDto {

	private String keyword;
	private String category;

}
