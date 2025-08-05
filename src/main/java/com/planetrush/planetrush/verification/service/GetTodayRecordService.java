package com.planetrush.planetrush.verification.service;

public interface GetTodayRecordService {

	/**
	 * 오늘 유저가 행성에 대한 인증 여부를 반환합니다.
	 * @param memberId 유저의 고유 id
	 * @param planetId 행성의 고유 id
	 * @return 인증 여부
	 */
	boolean getTodayRecord(Long memberId, Long planetId);
	
}
