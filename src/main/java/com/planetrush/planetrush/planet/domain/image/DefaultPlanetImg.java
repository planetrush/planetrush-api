package com.planetrush.planetrush.planet.domain.image;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 기본 제공 행성 이미지 엔티티 클래스입니다.
 * 고유 식별자, url, 업로드 일자 등을 포함합니다.
 */
@Getter
@Entity
@Table(name = "default_planet_img")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DefaultPlanetImg {

	/**
	 * 기본 제공 행성 이미지의 고유 식별자입니다.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "default_planet_img_id")
	private long id;

	/**
	 * 기본 제공 행성 이미지의 파일 url입니다.
	 */
	@Column(name = "img_url", nullable = false)
	private String ImgUrl;

	/**
	 * 기본 제공 행성 이미지의 파일 업로드 일자입니다.
	 * 자동으로 현재 타임스탬프로 설정됩니다.
	 */
	@CreationTimestamp
	@Column(name = "upload_date", nullable = false)
	private LocalDateTime uploadDate;
}
