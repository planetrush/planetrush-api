package com.planetrush.planetrush.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.planetrush.planetrush.member.domain.Member;
import com.planetrush.planetrush.member.domain.Provider;
import com.planetrush.planetrush.member.domain.Status;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Member findByEmailAndProviderAndStatus(String email, Provider provider, Status status);
}
