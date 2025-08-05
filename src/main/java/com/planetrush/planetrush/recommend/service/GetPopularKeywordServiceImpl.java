package com.planetrush.planetrush.recommend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planetrush.planetrush.planet.domain.Category;
import com.planetrush.planetrush.recommend.domain.PopularKeyword;
import com.planetrush.planetrush.recommend.repository.PopularKeywordRepository;
import com.planetrush.planetrush.recommend.service.dto.GetPopularKeywordDto;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class GetPopularKeywordServiceImpl implements GetPopularKeywordService {

	private final PopularKeywordRepository popularKeywordRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<GetPopularKeywordDto> getPopularKeywords(String category) {
		List<PopularKeyword> keywordList = popularKeywordRepository.findByCategory(Category.valueOf(category));
		return keywordList.stream()
			.map(popularKeyword -> GetPopularKeywordDto.builder()
				.keyword(popularKeyword.getKeyword())
				.category(popularKeyword.getCategory().toString())
				.build())
			.collect(Collectors.toList());
	}

}
