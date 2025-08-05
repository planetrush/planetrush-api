package com.planetrush.planetrush.planet.domain;

import com.planetrush.planetrush.member.domain.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 행성 거주자 정보를 나타내는 엔티티 클래스입니다.
 * 고유 식별자, 참여자, 행성, 챌린지 참여 상태, 퇴출 여부, 행성 생성자 여부 등을 포함합니다.
 */
@Getter
@Entity
@Table(name = "resident")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Resident {

	/**
	 * 행성 거주자의 고유 식별자입니다.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "resident_id")
	private Long id;

	/**
	 * 거주 사용자의 정보를 나타냅니다.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	/**
	 * 거주하는 planet의 정보를 나타냅니다.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "planet_id")
	private Planet planet;

	/**
	 * 행성 퇴출 여부를 나타냅니다.
	 */
	@Column(name = "is_banned", nullable = false)
	private Boolean banned;

	/**
	 * 행성 창조자 여부를 나타냅니다.
	 */
	@Column(name = "is_creator", nullable = false)
	private Boolean creator;

	/**
	 * {@code Resident} 객체를 생성하는 프라이빗 생성자입니다.
	 * <p>
	 * 주어진 {@code Member}와 {@code Planet}을 추가합니다.
	 * {@code ChallengerStatus}, {@code banned} 및 {@code creator} 필드도 초기화됩니다.
	 * </p>
	 *
	 * @param member 거주민이 될 {@code Member} 객체
	 * @param planet 거주민이 속할 {@code Planet} 객체
	 * @param challengerStatus 거주민의 상태
	 * @param banned 행성 퇴출 여부
	 * @param creator 행성 창조 여부
	 */
	private Resident(Member member, Planet planet, Boolean banned, Boolean creator) {
		addMember(member);
		addPlanet(planet);
		this.banned = banned;
		this.creator = creator;
	}

	/**
	 * 행성 창조자가 아닌 사용자의 거주 정보를 등록합니다.
	 * @param member 거주할 사용자의 정보
	 * @param planet 거주할 행성의 정보
	 * @return 행성 거주 정보 객체를 반환
	 */
	public static Resident isNotCreator(Member member, Planet planet) {
		return new Resident(member, planet, false, false);
	}

	/**
	 * 행성 창조자의 거주 정보를 등록합니다.
	 * @param member 거주할 사용자의 정보
	 * @param planet 거주할 행성의 정보
	 * @return 행성 거주 정보 객체를 반환
	 */
	public static Resident isCreator(Member member, Planet planet) {
		return new Resident(member, planet, false, true);
	}

	/**
	 * 연관관계에 있는 행성에 해당 거주 정보를 추가합니다.
	 * @param planet 거주 중인 행성 정보
	 */
	private void addPlanet(Planet planet) {
		this.planet = planet;
		planet.getResidents().add(this);
	}

	/**
	 * 연관관계에 있는 사용자 정보에 해당 거주 정보를 추가합니다.
	 * @param member 거주 하는 사용자 정보
	 */
	private void addMember(Member member) {
		this.member = member;
		member.getResidents().add(this);
	}

}
