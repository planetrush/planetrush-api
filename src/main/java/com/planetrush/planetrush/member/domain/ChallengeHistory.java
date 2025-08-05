package com.planetrush.planetrush.member.domain;

import com.planetrush.planetrush.planet.domain.Category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "challenge_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChallengeHistory {

	/**
	 * 챌린지 내역의 고유 식별자입니다.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "challenge_history_id")
	private Long id;

	/**
	 * 유저에 대한 정보입니다.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	/**
	 * 행성 이름을 나타냅니다.
	 */
	@Column(name = "planet_name", nullable = false)
	private String planetName;

	/**
	 * 행성 이미지의 url을 나타냅니다.
	 */
	@Column(name = "planet_img_url", nullable = false)
	private String planetImgUrl;

	/**
	 * 챌린지의 내용을 나타냅니다.
	 */
	@Column(name = "challenge_content", nullable = false)
	private String challengeContent;

	/**
	 * 해당하는 행성의 카테고리를 나타냅니다.
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "category", nullable = false)
	private Category category;

	/**
	 * 행성에 대한 유저의 참여도를 나타냅니다.
	 */
	@Column(name = "progress", nullable = false)
	private Double progress;

	/**
	 * 챌린지의 성공, 실패 여부를 나타냅니다.
	 */
	@Enumerated(EnumType.STRING)
	@Column(name= "challenge_result", nullable = false)
	private ChallengeResult result;

	@Builder
	public ChallengeHistory(Member member, String planetName, String planetImgUrl, String challengeContent,
		Category category, Double progress, ChallengeResult result) {
		this.member = member;
		this.planetName = planetName;
		this.planetImgUrl = planetImgUrl;
		this.challengeContent = challengeContent;
		this.category = category;
		this.progress = progress;
		this.result = result;
	}

	/**
	 * 챌린지 결과를 성공으로 저장합니다.
	 */
	public void successResult(){
		this.result = ChallengeResult.SUCCESS;
	}

	/**
	 * 챌린지 결과를 실패로 저장합니다.
	 */
	public void failResult(){
		this.result = ChallengeResult.FAIL;
	}

}
