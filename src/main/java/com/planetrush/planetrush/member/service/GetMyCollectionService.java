package com.planetrush.planetrush.member.service;

import java.util.List;

import com.planetrush.planetrush.member.service.dto.CollectionSearchCond;
import com.planetrush.planetrush.member.service.dto.PlanetCollectionDto;

public interface GetMyCollectionService {

	/**
	 * 주어진 memberId를 사용하여 완료한 행성의 기록을 검색합니다.
	 * @return 컬렉션 리스트를 반환합니다.
	 */
	List<PlanetCollectionDto> getPlanetCollections(CollectionSearchCond searchCond);
}
