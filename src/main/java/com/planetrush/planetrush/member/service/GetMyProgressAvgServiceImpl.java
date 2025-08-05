package com.planetrush.planetrush.member.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planetrush.planetrush.infra.flask.util.FlaskUtil;
import com.planetrush.planetrush.member.domain.Member;
import com.planetrush.planetrush.member.exception.MemberNotFoundException;
import com.planetrush.planetrush.member.repository.MemberRepository;
import com.planetrush.planetrush.member.service.dto.GetMyProgressAvgDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GetMyProgressAvgServiceImpl implements GetMyProgressAvgService {

	private final MemberRepository memberRepository;
	private final FlaskUtil flaskUtil;

	/**
	 * {@inheritDoc}
	 *
	 * <p>이 메서드는 반환값을 캐싱하여 관리합니다.</p>
	 * <p>캐시 미스가 발생하는 경우에만 플라스크 서버로 API 요청을 전송하여 새로운 데이터로 캐시에 저장합니다.</p>
	 */
	@Cacheable(value = "myProgressAvgCache", key = "#memberId")
	@Override
	public GetMyProgressAvgDto getMyProgressAvgPer(Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException("Member not found with ID: " + memberId));
		return flaskUtil.getMyProgressAvg(memberId);
	}
	
}
