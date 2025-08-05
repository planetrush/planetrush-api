package com.planetrush.planetrush.recommend.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.planetrush.planetrush.planet.domain.Category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "popular_keyword")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PopularKeyword {

	/**
	 * 키워드 고유 식별자입니다.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "keyword_id")
	private Long id;

	/**
	 * 키워드 내용입니다.
	 */
	@Column(name = "keyword", length = 20, nullable = false)
	private String keyword;

	/**
	 * 카테고리입니다.
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "category", nullable = false)
	private Category category;

	/**
	 * 키워드 생성 일자입니다.
	 * 자동으로 현재 타임스탬프로 설정됩니다.
	 */
	@CreationTimestamp
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	/**
	 * 키워드 객체를 생성하는 생성자 입니다.
	 * @param keyword 키워드 내용
	 * @param category 카테고리
	 */
	public PopularKeyword(String keyword, Category category) {
		this.keyword = keyword;
		this.category = category;
	}

}