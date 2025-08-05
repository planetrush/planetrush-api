package com.planetrush.planetrush.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.planetrush.planetrush.member.domain.ChallengeHistory;

public interface ChallengeHistoryRepository extends JpaRepository<ChallengeHistory, Long> {

	Optional<List<ChallengeHistory>> findByMemberId(Long id);
}
