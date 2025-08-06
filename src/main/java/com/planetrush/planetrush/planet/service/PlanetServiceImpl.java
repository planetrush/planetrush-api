package com.planetrush.planetrush.planet.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planetrush.planetrush.member.domain.Member;
import com.planetrush.planetrush.member.exception.MemberNotFoundException;
import com.planetrush.planetrush.member.repository.MemberRepository;
import com.planetrush.planetrush.planet.domain.Category;
import com.planetrush.planetrush.planet.domain.Planet;
import com.planetrush.planetrush.planet.domain.Resident;
import com.planetrush.planetrush.planet.domain.image.DefaultPlanetImg;
import com.planetrush.planetrush.planet.exception.InvalidStartDateException;
import com.planetrush.planetrush.planet.exception.PlanetNotFoundException;
import com.planetrush.planetrush.planet.exception.ResidentAlreadyExistsException;
import com.planetrush.planetrush.planet.exception.ResidentNotFoundException;
import com.planetrush.planetrush.planet.exception.ResidentOverflowException;
import com.planetrush.planetrush.planet.repository.DefaultPlanetImgRepository;
import com.planetrush.planetrush.planet.repository.PlanetRepository;
import com.planetrush.planetrush.planet.repository.ResidentRepository;
import com.planetrush.planetrush.planet.repository.custom.PlanetRepositoryCustom;
import com.planetrush.planetrush.planet.repository.custom.ResidentRepositoryCustom;
import com.planetrush.planetrush.planet.service.dto.GetDefaultPlanetImgDto;
import com.planetrush.planetrush.planet.service.dto.GetMainPlanetListDto;
import com.planetrush.planetrush.planet.service.dto.GetMyPlanetListDto;
import com.planetrush.planetrush.planet.service.dto.OngoingPlanetDto;
import com.planetrush.planetrush.planet.service.dto.PlanetDetailDto;
import com.planetrush.planetrush.planet.service.dto.PlanetSubscriptionDto;
import com.planetrush.planetrush.planet.service.dto.RegisterPlanetDto;
import com.planetrush.planetrush.planet.service.dto.ResidentDto;
import com.planetrush.planetrush.planet.service.dto.SearchCond;
import com.planetrush.planetrush.planet.service.vo.GetMainPlanetListVo;
import com.planetrush.planetrush.verification.domain.VerificationRecord;
import com.planetrush.planetrush.verification.repository.custom.VerificationRecordRepositoryCustom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlanetServiceImpl implements PlanetService {

	private final MemberRepository memberRepository;
	private final PlanetRepository planetRepository;
	private final ResidentRepository residentRepository;
	private final DefaultPlanetImgRepository defaultPlanetImgRepository;

	private final PlanetRepositoryCustom planetRepositoryCustom;
	private final ResidentRepositoryCustom residentRepositoryCustom;
	private final VerificationRecordRepositoryCustom verificationRecordRepositoryCustom;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<GetDefaultPlanetImgDto> getAllImgUrls() {
		List<DefaultPlanetImg> images = defaultPlanetImgRepository.findAll();
		return images.stream()
			.map(img -> GetDefaultPlanetImgDto.builder()
				.imgId(img.getId())
				.imgUrl(img.getImgUrl())
				.build())
			.toList();
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>이 메서드는 주어진 검색 조건에 따라 챌린지 행성 정보를 검색합니다.</p>
	 * <p>검색 조건에는 키워드, 카테고리, 페이지 크기, 마지막 조회된 행성 id가 포함됩니다.</p>
	 * <p>검색 결과는 챌린지가 READY 상태인 것만 조회하여 최신순으로 반환됩니다.</p>
	 *
	 * @param cond 검색 조건(키워드, 카테고리, 페이지 크기, 마지막 조회 행성 id)
	 * @return 검색 결과가 담긴 목록
	 */
	@Override
	public List<PlanetDetailDto> searchPlanet(SearchCond cond) {
		List<Planet> planets = planetRepositoryCustom.searchPlanet(cond);
		return planets.stream()
			.map(p -> PlanetDetailDto.builder()
				.planetId(p.getId())
				.name(p.getName())
				.planetImg(p.getPlanetImg())
				.content(p.getContent())
				.startDate(p.getStartDate())
				.endDate(p.getEndDate())
				.category(String.valueOf(p.getCategory()))
				.maxParticipants(p.getMaxParticipants())
				.currentParticipants(p.getCurrentParticipants())
				.planetStatus(p.getStatus().toString())
				.build())
			.collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param memberId 행성 상세 정보를 요청하는 회원의 id; null일 수 있습니다.
	 * @param planetId 상세 정보를 요청하는 행성의 id
	 * @return 행성에 대한 상세 정보를 포함하는 {@link PlanetDetailDto}.
	 * @throws PlanetNotFoundException 지정된 ID를 가진 행성을 찾을 수 없는 경우.
	 * @throws MemberNotFoundException 지정된 ID를 가진 회원을 찾을 수 없는 경우 (memberId가 null이 아닌 경우).
	 */
	@Override
	public PlanetDetailDto getPlanetDetail(Long memberId, Long planetId) {
		Planet planet = planetRepository.findById(planetId)
			.orElseThrow(() -> new PlanetNotFoundException("Planet not found with ID: " + planetId));
		boolean joined = false;
		if (memberId != null) {
			Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new MemberNotFoundException("Member not found with ID: " + memberId));
			joined = residentRepositoryCustom.isResidentOfPlanet(member, planet);
		}
		return PlanetDetailDto.builder()
			.planetId(planet.getId())
			.name(planet.getName())
			.planetImg(planet.getPlanetImg())
			.standardVerificationImg(planet.getStandardVerificationImg())
			.verificationCond(planet.getVerificationCond())
			.content(planet.getContent())
			.startDate(planet.getStartDate())
			.endDate(planet.getEndDate())
			.category(String.valueOf(planet.getCategory()))
			.maxParticipants(planet.getMaxParticipants())
			.currentParticipants(planet.getCurrentParticipants())
			.planetStatus(String.valueOf(planet.getStatus()))
			.joined(joined)
			.build();
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>이 메서드는 특정 멤버와 플래닛에 대한 진행 중인 플래닛 정보를 반환합니다.</p>
	 *
	 * <p>연속으로 3일간 인증하지 않은 회원은 행성에서 퇴출되며, 거주자 목록에서 제외됩니다.</p>
	 * <p>거주자 목록은 각 거주자의 인증 횟수와 연속 인증 점수를 기준으로 정렬됩니다.</p>
	 * <p>연속 인증 점수는 기본 1점에서 시작하며, 연속된 일 수만큼 매일 0.1 포인트를 추가하여 계산합니다.</p>
	 *
	 * @param memberId 멤버의 ID
	 * @param planetId 플래닛의 ID
	 * @return OngoingPlanetDto 객체, 진행 중인 플래닛의 정보를 담고 있습니다.
	 * @throws MemberNotFoundException 멤버를 찾지 못했을 때 발생
	 * @throws PlanetNotFoundException 플래닛을 찾지 못했을 때 발생
	 */
	@Override
	public OngoingPlanetDto getOngoingPlanet(Long memberId, Long planetId) {
		Member querriedMember = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException("Member not found with ID: " + memberId));
		Planet planet = planetRepository.findById(planetId)
			.orElseThrow(() -> new PlanetNotFoundException("Planet not found with ID: " + planetId));
		List<Resident> residents = residentRepositoryCustom.getResidentsNotBanned(planet);
		VerificationRecord todayRecord = verificationRecordRepositoryCustom.findTodayRecord(querriedMember, planet);
		return OngoingPlanetDto.builder()
			.planetId(planet.getId())
			.planetImg(planet.getPlanetImg())
			.standardVerificationImg(planet.getStandardVerificationImg())
			.category(planet.getCategory().toString())
			.content(planet.getContent())
			.startDate(planet.getStartDate())
			.endDate(planet.getEndDate())
			.name(planet.getName())
			.totalVerificationCnt(planet.calcTotalVerificationCnt())
			.residents(toResidentDto(residents, querriedMember))
			.isVerifiedToday(todayRecord != null)
			.build();
	}

	/**
	 * Resident 리스트를 ResidentDto 리스트로 변환합니다.
	 *
	 * @param residents 변환할 Resident 리스트
	 * @param qurriedMember 현재 조회 중인 회원
	 * @return 변환된 ResidentDto 리스트를 반환
	 */
	private List<ResidentDto> toResidentDto(List<Resident> residents,
		Member qurriedMember) {
		List<ResidentDto> dtoList = residents.stream()
			.map(resident -> {
				Member member = resident.getMember();
				Planet planet = resident.getPlanet();
				List<VerificationRecord> verificationInfo = verificationRecordRepositoryCustom.findVerificationRecordsByMemberIdAndPlanetId(
					member, planet);
				return ResidentDto.builder()
					.memberId(member.getId())
					.nickname(member.getNickname())
					.querriedMember(qurriedMember.equals(member))
					.verificationCnt(verificationInfo.size())
					.verificationContinuityPoint(calcContinuityPoint(verificationInfo))
					.build();
			})
			.sorted((o1, o2) -> {
				if (o1.getVerificationCnt() == o2.getVerificationCnt()) {
					return Double.compare(o1.getVerificationContinuityPoint(), o2.getVerificationContinuityPoint())
						* -1;
				}
				return Integer.compare(o1.getVerificationCnt(), o2.getVerificationCnt()) * -1;
			})
			.toList();
		return dtoList;
	}

	/**
	 * 검증 기록 리스트를 기반으로 연속성 포인트를 계산합니다.
	 *
	 * @param list 검증 기록 리스트
	 * @return 리스트가 비어있거나 null일 경우 0을, 아니라면 계산된 연속성 포인트를 반환
	 */
	private double calcContinuityPoint(List<VerificationRecord> list) {
		if (list == null || list.isEmpty()) {
			return 0;
		}
		double sum = 1.0;
		double stdPoint = 1.0;
		for (int idx = 1; idx < list.size(); idx++) {
			VerificationRecord beforeRecord = list.get(idx - 1);
			VerificationRecord currentRecord = list.get(idx);
			LocalDate beforeDate = beforeRecord.getUploadDate().toLocalDate();
			LocalDate currentDate = currentRecord.getUploadDate().toLocalDate();
			long daysBetween = ChronoUnit.DAYS.between(beforeDate, currentDate);
			log.info("beforeDate = {}", beforeDate);
			log.info("currentDate = {}", currentDate);
			log.info("daysBetween = {}", daysBetween);
			if (daysBetween == 1) {
				stdPoint += 0.1;
			} else {
				stdPoint = 1.0;
			}
			sum += stdPoint;
		}
		return sum;
	}

	/**
	 * {@inheritDoc}
	 * @param memberId
	 * @return
	 */
	@Override
	public List<GetMyPlanetListDto> getMyPlanetList(Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException("Member not found with ID: " + memberId));
		return planetRepositoryCustom.getMyPlanetList(member);
	}

	/**
	 * {@inheritDoc}
	 * @param memberId
	 * @return
	 */
	@Override
	public List<GetMainPlanetListDto> getMainPlanetList(Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException("Member not found with ID: " + memberId));
		List<GetMainPlanetListVo> planetList = planetRepositoryCustom.getMainPlanetList(member);
		return planetList.stream()
			.map(this::convertToDto)
			.toList();
	}

	/**
	 * 마지막 인증 날짜 혹은 행성 시작 날짜와 오늘 날짜를 비교해 lastDay 변수에 담아줍니다.
	 * @param vo GetMainPlanetListVo
	 * @return GetMainPlanetListDto
	 */
	private GetMainPlanetListDto convertToDto(GetMainPlanetListVo vo) {
		LocalDate today = LocalDate.now();
		LocalDateTime lastVerifyDate = vo.getLastVerifyDate();
		LocalDate planetStartDate = vo.getPlanetStartDate();
		boolean isLastDay = false;
		long daysBetween = ChronoUnit.DAYS.between(planetStartDate, today);
		isLastDay = daysBetween >= 2;
		if (lastVerifyDate != null) {
			daysBetween = ChronoUnit.DAYS.between(lastVerifyDate.toLocalDate(), today);
			isLastDay = daysBetween >= 3;
		}
		return GetMainPlanetListDto.builder()
			.planetId(vo.getPlanetId())
			.planetImgUrl(vo.getPlanetImgUrl())
			.name(vo.getName())
			.status(vo.getStatus())
			.lastDay(isLastDay)
			.build();
	}

	/**
	 * {@inheritDoc}
	 *
	 * 이 메서드는 데이터베이스에 입주기록을 등록합니다.
	 * @param dto 사용자의 id, 행성의 id 등을 포함합니다.
	 */
	@Transactional
	@Override
	public void registerResident(PlanetSubscriptionDto dto) {
		Member member = memberRepository.findById(dto.getMemberId())
			.orElseThrow(() -> new MemberNotFoundException("Member not found with ID: " + dto.getMemberId()));
		Planet planet = planetRepository.findByIdForUpdate(dto.getPlanetId())
			.orElseThrow(() -> new PlanetNotFoundException("Planet not found with ID: " + dto.getPlanetId()));
		if(residentRepositoryCustom.getReadyAndInProgressResidents(member) >= 9) {
			throw new ResidentOverflowException("resident count overflow");
		}
		residentRepository.findByMemberIdAndPlanetId(member.getId(), planet.getId())
			.ifPresent(resident -> {
				throw new ResidentAlreadyExistsException("resident already exists: " + resident.getId());
			});
		planet.addParticipant();
		residentRepository.save(Resident.isNotCreator(member, planet));
	}

	/**
	 * {@inheritDoc}
	 *
	 * 이 메서드는 데이터베이스에서 입주 정보를 삭제합니다.
	 * @param dto 사용자의 id, 행성의 id 등을 포함합니다.
	 */
	@Transactional
	@Override
	public void deleteResident(PlanetSubscriptionDto dto) {
		Resident resident = residentRepository.findByMemberIdAndPlanetId(dto.getMemberId(), dto.getPlanetId())
			.orElseThrow(() -> new ResidentNotFoundException(
				"Resident not found member id: " + dto.getMemberId() + " and planet id: " + dto.getPlanetId()));
		Planet planet = planetRepository.findById(dto.getPlanetId())
			.orElseThrow(() -> new PlanetNotFoundException("Planet not found with ID: " + dto.getPlanetId()));
		planet.participantLeave();
		residentRepository.delete(resident);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override
	public void registerPlanet(RegisterPlanetDto dto) {
		Member member = memberRepository.findById(dto.getMemberId())
			.orElseThrow(() -> new MemberNotFoundException("Member not found with ID: " + dto.getMemberId()));
		if(ChronoUnit.DAYS.between(LocalDate.now(), dto.getStartDate()) > 14) {
			throw new InvalidStartDateException("Start date must be within 14 days from today.");
		}
		if(residentRepositoryCustom.getReadyAndInProgressResidents(member) >= 9) {
			throw new ResidentOverflowException("resident count overflow");
		}
		Planet planet = planetRepository.save(Planet.builder()
			.name(dto.getName())
			.category(Category.valueOf(dto.getCategory()))
			.content(dto.getContent())
			.startDate(dto.getStartDate())
			.endDate(dto.getEndDate())
			.maxParticipants(dto.getMaxParticipants())
			.verificationCond(dto.getAuthCond())
			.planetImg(dto.getPlanetImgUrl())
			.standardVerificationImg(dto.getStandardVerificationImgUrl())
			.build());
		residentRepository.save(Resident.isCreator(member, planet));
	}
}
