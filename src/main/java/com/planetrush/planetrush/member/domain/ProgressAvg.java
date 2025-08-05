package com.planetrush.planetrush.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "progress_avg")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgressAvg {

	/**
	 * 완주율 고유 식별자입니다.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "progress_id")
	private int id;

	/**
	 * 유저에 대한 정보입니다.
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	/**
	 * 모든 카테고리의 완주율 평균을 나타냅니다.
	 */
	@Column(name = "total_avg", nullable = false)
	private Double totalAvg;

	/**
	 * BEAUTY 카테고리의 완주율 평균을 나타냅니다.
	 */
	@Column(name = "beauty_avg", nullable = false)
	private Double beautyAvg;

	/**
	 * EXERCISE 카테고리의 완주율 평균을 나타냅니다.
	 */
	@Column(name = "exercise_avg", nullable = false)
	private Double exerciseAvg;

	/**
	 * LIFE 카테고리의 완주율 평균을 나타냅니다.
	 */
	@Column(name = "life_avg", nullable = false)
	private Double lifeAvg;

	/**
	 * STUDY 카테고리의 완주율 평균을 나타냅니다.
	 */
	@Column(name = "study_avg", nullable = false)
	private Double studyAvg;

	/**
	 * ETC 카테고리의 완주율 평균을 나타냅니다.
	 */
	@Column(name = "etc_avg", nullable = false)
	private Double etcAvg;

	@Builder
	public ProgressAvg(Member member, Double totalAvg, Double beautyAvg,
		Double exerciseAvg, Double lifeAvg, Double studyAvg, Double etcAvg) {
		this.member = member;
		this.totalAvg = totalAvg;
		this.beautyAvg = beautyAvg;
		this.exerciseAvg = exerciseAvg;
		this.lifeAvg = lifeAvg;
		this.studyAvg = studyAvg;
		this.etcAvg = etcAvg;
	}

}
