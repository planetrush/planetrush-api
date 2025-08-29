package com.planetrush.planetrush.core.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Configuration
public class CacheConfig {

	@Bean
	public CacheManager cacheManager() {
		CaffeineCacheManager cacheManager = new CaffeineCacheManager();
		CacheType[] values = CacheType.values();
		for (CacheType ct : CacheType.values()) {
			cacheManager.registerCustomCache(ct.getCacheName(),
				Caffeine.newBuilder().expireAfterWrite(ct.getDuration(), ct.getTimeUnit()).build());
		}
		return cacheManager;
	}

	@Getter
	@AllArgsConstructor
	enum CacheType {
		CHALLENGE_AVG("challenge-avg", 24, TimeUnit.HOURS);

		final String cacheName;
		final int duration;
		final TimeUnit timeUnit;
	}
}