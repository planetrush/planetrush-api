package com.planetrush.planetrush.member;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.planetrush.planetrush.ServiceUnitTest;
import com.planetrush.planetrush.infra.flask.util.FlaskApiClient;
import com.planetrush.planetrush.member.repository.MemberRepository;
import com.planetrush.planetrush.member.repository.custom.ChallengeHistoryRepositoryCustom;
import com.planetrush.planetrush.member.service.MemberServiceImpl;

public abstract class MemberServiceUnitTest extends ServiceUnitTest {

	@InjectMocks
	protected MemberServiceImpl memberService;

	@Mock
	protected FlaskApiClient flaskApiClient;

	@Mock
	protected MemberRepository memberRepository;

	@Mock
	protected ChallengeHistoryRepositoryCustom challengeHistoryRepositoryCustom;
}
