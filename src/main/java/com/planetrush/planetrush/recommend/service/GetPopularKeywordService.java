package com.planetrush.planetrush.recommend.service;

import java.util.List;

import com.planetrush.planetrush.recommend.service.dto.GetPopularKeywordDto;

public interface GetPopularKeywordService {

	/**
	 * 카테고리별 추천 키워드를 조회합니다.
	 * @param category 카테고리
	 * @return 해당 카테코리의 추천 키워드 목록
	 */
	List<GetPopularKeywordDto> getPopularKeywords(String category);

}
