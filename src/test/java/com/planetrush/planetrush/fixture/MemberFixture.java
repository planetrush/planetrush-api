package com.planetrush.planetrush.fixture;

import java.util.ArrayList;
import java.util.List;

import com.planetrush.planetrush.member.domain.Member;
import com.planetrush.planetrush.member.domain.Provider;
import com.planetrush.planetrush.member.domain.Status;

public final class MemberFixture {

	public static Member activeMember() {
		return Member.builder()
			.ci("ci")
			.email("test@email.com")
			.nickname("회원1")
			.provider(Provider.KAKAO)
			.status(Status.ACTIVE)
			.build();
	}

	public static Member activeMember(int id) {
		return Member.builder()
			.ci("ci")
			.email("test" + id + "@email.com")
			.nickname("회원" + id)
			.provider(Provider.KAKAO)
			.status(Status.ACTIVE)
			.build();
	}

	public static List<Member> activeMembers(int cnt) {
		List<Member> members = new ArrayList<>();
		for (int i = 1; i <= cnt; i++) {
			members.add(activeMember(i));
		}
		return members;
	}
}
