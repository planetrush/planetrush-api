package com.planetrush.planetrush.verification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.planetrush.planetrush.verification.domain.VerificationRecord;

public interface VerificationRecordRepository extends JpaRepository<VerificationRecord, Long> {
	
}
