package com.planetrush.planetrush.planet.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.planetrush.planetrush.planet.exception.NegativeParticipantCountException;
import com.planetrush.planetrush.planet.exception.ParticipantsOverflowException;
import com.planetrush.planetrush.planet.exception.PlanetDestroyedException;
import com.planetrush.planetrush.planet.exception.RegisterResidentTimeoutException;
import com.planetrush.planetrush.planet.exception.ResidentExitTimeoutException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 행성 정보를 나타내는 엔티티 클래스입니다.
 * 고유 식별자, 행성명, 카테고리, 챌린지명, 시작일자, 종료일자, 최대 참여자, 현재 참여자, 참여 멤버,
 * 행성 상태, 생성 일자, 행성 이미지, 챌린지 인증 예시 이미지 등을 포함합니다.
 */
@Getter
@Entity
@Table(name = "planet")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Planet {

	/**
	 * 행성의 고유 식별자입니다.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "planet_id")
	private Long id;

	/**
	 * 행성의 이름입니다.
	 */
	@Column(name = "name", length = 10, nullable = false)
	private String name;

	/**
	 * 행성의 카테고리입니다.
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "category", nullable = false)
	private Category category;

	/**
	 * 행성이 도전하는 챌린지의 내용입니다.
	 */
	@Column(name = "challenge_content", nullable = false)
	private String content;

	/**
	 * 챌린지가 시작되는 일자입니다.
	 */
	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	/**
	 * 챌린지가 종료되는 일자입니다.
	 */
	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;

	/**
	 * 최대로 참여할 수 있는 인원입니다.
	 */
	@Column(name = "max_participants", nullable = false)
	private int maxParticipants;

	/**
	 * 현재 참여 중인 인원입니다.
	 */
	@Column(name = "current_participants", nullable = false)
	private int currentParticipants;

	/**
	 * 챌린지 인증을 위한 조건에 대한 설명입니다.
	 */
	@Column(name = "verification_cond", nullable = false)
	private String verificationCond;

	/**
	 * 행성에 참여 중인 member들입니다.
	 */
	@OneToMany(mappedBy = "planet")
	private final List<Resident> residents = new ArrayList<>();

	/**
	 * 행성의 상태 정보입니다.
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "planet_status", nullable = false)
	private PlanetStatus status;

	/**
	 * 행성의 생성 일자입니다.
	 * 자동으로 현재 타임스탬프로 설정됩니다.
	 */
	@CreationTimestamp
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	/**
	 * 행성 대표 이미지 URL 정보입니다.
	 */
	@Column(name = "planet_img_url", length = 300)
	private String planetImg;

	/**
	 * 챌린지를 인증하기 위한 예시 이미지입니다.
	 */
	@Column(name = "standard_verification_img", length = 300)
	private String standardVerificationImg;

	/**
	 * 행성 객체를 생성하는 생성자입니다.
	 * <p>
	 * 기본 생성자이며, 행성의 현재 참가자 수를 1로 설정하고, 상태를 {@code PlanetStatus.READY}로 설정합니다.
	 * </p>
	 *
	 * @param name 행성의 이름
	 * @param category 행성의 카테고리
	 * @param content 행성의 내용
	 * @param startDate 행성의 시작 날짜
	 * @param endDate 행성의 종료 날짜
	 * @param maxParticipants 최대 참가자 수
	 * @param verificationCond 인증 조건
	 * @param planetImg 기본 행성 이미지
	 * @param standardVerificationImg 인증 기준 사진
	 */
	@Builder
	public Planet(String name, Category category, String content, LocalDate startDate, LocalDate endDate,
		int maxParticipants, String verificationCond, String planetImg, String standardVerificationImg) {
		this(name, category, content, startDate, endDate, maxParticipants, 1, verificationCond, PlanetStatus.READY,
			planetImg, standardVerificationImg);
	}

	/**
	 * 행성 객체를 생성하는 프라이빗 생성자입니다.
	 * <p>
	 * 모든 필드를 초기화합니다. 현재 참가자 수와 상태를 포함하여 기본 값으로 설정합니다.
	 * </p>
	 *
	 * @param name 행성의 이름
	 * @param category 행성의 카테고리
	 * @param content 행성의 내용
	 * @param startDate 행성의 시작 날짜
	 * @param endDate 행성의 종료 날짜
	 * @param maxParticipants 최대 참가자 수
	 * @param currentParticipants 현재 참가자 수
	 * @param verificationCond 인증 조건
	 * @param status 행성의 상태
	 * @param planetImg 행성 이미지
	 * @param standardVerificationImg 인증 예시 이미지
	 */
	public Planet(String name, Category category, String content, LocalDate startDate, LocalDate endDate,
		int maxParticipants, int currentParticipants, String verificationCond, PlanetStatus status, String planetImg,
		String standardVerificationImg) {
		this.name = name;
		this.category = category;
		this.content = content;
		this.startDate = startDate;
		this.endDate = endDate;
		this.maxParticipants = maxParticipants;
		this.currentParticipants = currentParticipants;
		this.verificationCond = verificationCond;
		this.status = status;
		this.planetImg = planetImg;
		this.standardVerificationImg = standardVerificationImg;
	}

	/**
	 * 현재 행성의 참가자 수를 1 증가시킵니다.
	 * <p>
	 * 최대 참가자 수를 초과하면 {@code ParticipantsOverflowException}이 발생합니다.
	 * </p>
	 *
	 * @throws RegisterResidentTimeoutException 챌린지 시작 일자를 지났을 경우
	 * @throws ParticipantsOverflowException 최대 참가자 수를 초과할 경우
	 */
	public void addParticipant() {
		checkDestroyed();
		if (LocalDate.now().isAfter(this.startDate) || LocalDate.now().equals(this.startDate)) {
			throw new RegisterResidentTimeoutException();
		}
		if (currentParticipants == maxParticipants) {
			throw new ParticipantsOverflowException();
		}
		this.currentParticipants++;
	}

	/**
	 * 현재 행성의 참가자 수를 1 감소시킵니다.
	 * 챌린지 시작 전 행성을 떠날 수 있습니다.
	 *
	 * <p>
	 * 참가자 수가 0이 되면 행성의 상태를 {@code PlanetStatus.DESTROYED}로 변경합니다.
	 * 참가자 수가 음수가 되면 {@code NegativeParticipantCountException}이 발생합니다.
	 * </p>
	 *
	 * @throws ResidentExitTimeoutException 챌린지가 시작하여 행성 탈퇴가 불가능한 경우
	 * @throws NegativeParticipantCountException 참가자 수가 음수가 될 경우
	 */
	public void participantLeave() {
		if (LocalDate.now().isAfter(this.startDate) || LocalDate.now().equals(this.startDate)) {
			throw new ResidentExitTimeoutException();
		}
		if (currentParticipants < 0) {
			throw new NegativeParticipantCountException();
		}
		this.currentParticipants--;
		if (currentParticipants == 0) {
			this.status = PlanetStatus.DESTROYED;
		}
	}

	/**
	 * 현재 행성의 참가자 수를 1 감소시킵니다.
	 * 인증을 하지 않는 참가자를 퇴출시킵니다.
	 *
	 * <p>
	 * 참가자 수가 0이 되면 행성의 상태를 {@code PlanetStatus.DESTROYED}로 변경합니다.
	 * 참가자 수가 음수가 되면 {@code NegativeParticipantCountException}이 발생합니다.
	 * </p>
	 *
	 * @throws NegativeParticipantCountException 참가자 수가 음수가 될 경우
	 */
	public void participantExpulsion() {
		if (currentParticipants < 0) {
			throw new NegativeParticipantCountException();
		}
		this.currentParticipants--;
		if (currentParticipants == 0) {
			this.status = PlanetStatus.DESTROYED;
		}
	}

	/**
	 * 현재 행성의 상태가 {@code DESTROYED}인지 확인합니다.
	 * 행성이 파괴된 경우 {@link PlanetDestroyedException}을 던집니다.
	 *
	 * @throws PlanetDestroyedException 행성의 상태가 {@code DESTROYED}인 경우
	 */
	private void checkDestroyed() {
		if (this.status == PlanetStatus.DESTROYED) {
			throw new PlanetDestroyedException("Planet is already destroyed : " + this.id);
		}
	}

	/**
	 * 두 날짜 사이의 일 수를 계산합니다.
	 *
	 * @return 시작 날짜와 종료 날짜 사이의 일 수
	 */
	public long calcTotalVerificationCnt() {
		return ChronoUnit.DAYS.between(this.startDate, this.endDate);
	}

	/**
	 * 행성이 시작된 후로 경과된 날짜를 계산합니다.
	 *
	 * (이 함수는 인증 기록이 없는 경우 자동 퇴소 처리를 위해 사용됩니다.)
	 *
	 * @return 행성이 시작된 이후 경과된 일 수
	 */
	public long calcElapsedPeriod() {
		return ChronoUnit.DAYS.between(this.startDate, LocalDate.now());
	}

	/**
	 * 완료 상태로 변경합니다.
	 */
	public void complete() {
		this.status = PlanetStatus.COMPLETED;
	}

	/**
	 * 파괴 상태로 변경합니다.
	 */
	public void destroy() {
		this.status = PlanetStatus.DESTROYED;
	}

}
