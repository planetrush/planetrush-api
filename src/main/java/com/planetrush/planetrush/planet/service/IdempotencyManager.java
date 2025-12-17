package com.planetrush.planetrush.planet.service;

import static com.planetrush.planetrush.planet.service.IdempotencyTTL.*;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class IdempotencyManager {

	private final RedisTemplate<String, String> redisTemplate;

	public boolean tryRegisterResident(Long memberId, Long planetId) {
		String key = generateKey(REGISTER_RESIDENT.getPrefix(), memberId, planetId);
		return Boolean.TRUE.equals(
			redisTemplate.opsForValue().setIfAbsent(
				key,
				"true",
				REGISTER_RESIDENT.amount,
				REGISTER_RESIDENT.unit
			)
		);
	}

	public boolean tryDeleteResident(Long memberId, Long planetId) {
		String key = generateKey(DELETE_RESIDENT.getPrefix(), memberId, planetId);
		return Boolean.TRUE.equals(
			redisTemplate.opsForValue().setIfAbsent(
				key,
				"true",
				DELETE_RESIDENT.amount,
				DELETE_RESIDENT.unit
			)
		);
	}

	private String generateKey(String prefix, Long memberId, Long planetId) {
		return new StringBuffer()
			.append(prefix)
			.append(":")
			.append(memberId)
			.append(":")
			.append(planetId)
			.toString();
	}
}

