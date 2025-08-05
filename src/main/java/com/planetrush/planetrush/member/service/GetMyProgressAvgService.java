package com.planetrush.planetrush.member.service;

import com.planetrush.planetrush.member.service.dto.GetMyProgressAvgDto;

public interface GetMyProgressAvgService {

	/**
	 * 마이페이지를 위한 현재 사용자와 전체 사용자의 통계 정보를 가져옵니다.
	 * @param memberId 현재 사용자의 id
	 * @return 마이페이지를 위한 정보
	 */
	GetMyProgressAvgDto getMyProgressAvgPer(Long memberId);

}
