package com.planetrush.planetrush.member.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planetrush.planetrush.member.domain.ChallengeHistory;
import com.planetrush.planetrush.member.repository.custom.ChallengeHistoryRepositoryCustom;
import com.planetrush.planetrush.member.service.dto.CollectionSearchCond;
import com.planetrush.planetrush.member.service.dto.PlanetCollectionDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GetMyCollectionServiceImpl implements GetMyCollectionService {

	private final ChallengeHistoryRepositoryCustom challengeHistoryRepositoryCustom;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PlanetCollectionDto> getPlanetCollections(CollectionSearchCond searchCond) {
		List<ChallengeHistory> historyList = challengeHistoryRepositoryCustom.getMyChallengeHistory(searchCond);
		return historyList.stream()
			.map(history -> PlanetCollectionDto.builder()
				.historyId(history.getId())
				.name(history.getPlanetName())
				.category(history.getCategory())
				.content(history.getChallengeContent())
				.imageUrl(history.getPlanetImgUrl())
				.progress(Double.parseDouble(String.format("%.2f", history.getProgress())))
				.build())
			.collect(Collectors.toList());
	}
}
