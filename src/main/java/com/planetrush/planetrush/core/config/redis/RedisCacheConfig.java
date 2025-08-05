package com.planetrush.planetrush.core.config.redis;

import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.planetrush.planetrush.member.service.dto.GetMyProgressAvgDto;

@Configuration
@EnableCaching
public class RedisCacheConfig {

	@Bean
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
			.entryTtl(Duration.ofHours(24))
			.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
			.serializeValuesWith(
				RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<>(
					GetMyProgressAvgDto.class)));
		return RedisCacheManager.builder(redisConnectionFactory)
			.cacheDefaults(cacheConfig)
			.build();
	}

}
