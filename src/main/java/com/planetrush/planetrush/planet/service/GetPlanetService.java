package com.planetrush.planetrush.planet.service;

import java.util.List;

import com.planetrush.planetrush.planet.service.dto.GetMainPlanetListDto;
import com.planetrush.planetrush.planet.service.dto.GetMyPlanetListDto;
import com.planetrush.planetrush.planet.service.dto.OngoingPlanetDto;
import com.planetrush.planetrush.planet.service.dto.PlanetDetailDto;
import com.planetrush.planetrush.planet.service.dto.SearchCond;

public interface GetPlanetService {

	/**
	 * 챌린지가 시작되지 않은 행성을 검색합니다.
	 *
	 * @param cond 검색 조건(키워드, 카테고리, 페이지 크기, 마지막 조회 행성 id)
	 * @return 검색 결과가 담긴 목록
	 */
	List<PlanetDetailDto> searchPlanet(SearchCond cond);

	/**
	 * 행성 상세정보를 조회합니다.
	 *
	 * @param memberId 유저의 고유 id
	 * @param planetId 조회하는 행성 id
	 * @return 행성 상세정보
	 */
	PlanetDetailDto getPlanetDetail(Long memberId, Long planetId);

	/**
	 * 현재 진행 중인 행성의 상세 정보를 조회합니다.
	 *
	 * @param memberId 유저의 고유 id
	 * @param planetId 조회 행성 id
	 * @return 현재 진행 중인 행성의 상세 정보가 담긴 객체
	 */
	OngoingPlanetDto getOngoingPlanet(Long memberId, Long planetId);

	/**
	 * 현재 사용자의 마이페이지를 위한 참여 중인 행성 목록을 조회합니다.
	 * @param memberId 유저의 고유 id
	 * @return 현재 사용자가 참여 중이며 진행 중인 행성의 상세 정보 목록
	 */
	List<GetMyPlanetListDto> getMyPlanetList(Long memberId);

	/**
	 * 현재 사용자의 마이페이지를 위한 참여 중인 행성 목록을 조회합니다.
	 * @param memberId 유저의 고유 id
	 * @return 현재 사용자가 참여 중이며 진행 중인 행성의 목록
	 */
	List<GetMainPlanetListDto> getMainPlanetList(Long memberId);

}
