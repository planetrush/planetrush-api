package com.planetrush.planetrush.member.service;

import java.util.List;

import com.planetrush.planetrush.member.service.dto.CollectionSearchCond;
import com.planetrush.planetrush.member.service.dto.GetMyProgressAvgDto;
import com.planetrush.planetrush.member.service.dto.PlanetCollectionDto;

public interface MemberService {

	/**
	 * 주어진 memberId를 사용하여 완료한 행성의 기록을 검색합니다.
	 * @return 컬렉션 리스트를 반환합니다.
	 */
	List<PlanetCollectionDto> getPlanetCollections(CollectionSearchCond searchCond);

	/**
	 * 마이페이지를 위한 현재 사용자와 전체 사용자의 통계 정보를 가져옵니다.
	 * @param memberId 현재 사용자의 id
	 * @return 마이페이지를 위한 정보
	 */
	GetMyProgressAvgDto getMyProgressAvgPer(Long memberId);

	/**
	 * 유저의 닉네임을 변경합니다.
	 * @param memberId 유저의 고유 id
	 * @param nickname 변경할 닉네임
	 */
	void updateMemberNickname(Long memberId, String nickname);
}
