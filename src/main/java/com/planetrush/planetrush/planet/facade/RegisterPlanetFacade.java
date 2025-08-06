package com.planetrush.planetrush.planet.facade;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.planetrush.planetrush.infra.s3.S3ImageService;
import com.planetrush.planetrush.infra.s3.dto.FileMetaInfo;
import com.planetrush.planetrush.member.domain.Member;
import com.planetrush.planetrush.member.exception.MemberNotFoundException;
import com.planetrush.planetrush.member.repository.MemberRepository;
import com.planetrush.planetrush.planet.exception.InvalidStartDateException;
import com.planetrush.planetrush.planet.exception.ResidentOverflowException;
import com.planetrush.planetrush.planet.facade.dto.RegisterPlanetFacadeDto;
import com.planetrush.planetrush.planet.repository.custom.ResidentRepositoryCustom;
import com.planetrush.planetrush.planet.service.PlanetService;
import com.planetrush.planetrush.planet.service.dto.RegisterPlanetDto;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Component
public class RegisterPlanetFacade {

	private final S3ImageService s3ImageService;
	private final PlanetService planetService;
	private final ResidentRepositoryCustom residentRepositoryCustom;
	private final MemberRepository memberRepository;

	/**
	 * 행성을 등록합니다.
	 *
	 * @param dto 행성 등록에 필요한 데이터를 담은 DTO입니다.
	 * @param customPlanetImg 사용자 정의 행성 이미지 파일입니다. 선택 사항입니다.
	 * @param stdVerificationImg 표준 검증 이미지 파일입니다.
	 */
	public void registerPlanet(RegisterPlanetFacadeDto dto, MultipartFile customPlanetImg,
		MultipartFile stdVerificationImg) {
		if(ChronoUnit.DAYS.between(LocalDate.now(), dto.getStartDate()) > 14) {
			throw new InvalidStartDateException();
		}
		Member member = memberRepository.findById(dto.getMemberId())
			.orElseThrow(() -> new MemberNotFoundException("Member not found with ID: " + dto.getMemberId()));
		if(residentRepositoryCustom.getReadyAndInProgressResidents(member) >= 9) {
			throw new ResidentOverflowException("resident count overflow");
		}
		String planetImgUrl = customPlanetImg == null ? dto.getPlanetImgUrl() :
			uploadCustomPlanetImg(customPlanetImg, dto.getMemberId());
		String stdVerificationImgUrl = uploadStdVerificationImg(stdVerificationImg, dto.getMemberId());
		planetService.registerPlanet(RegisterPlanetDto.builder()
			.name(dto.getName())
			.memberId(dto.getMemberId())
			.startDate(dto.getStartDate())
			.endDate(dto.getEndDate())
			.content(dto.getContent())
			.maxParticipants(dto.getMaxParticipants())
			.category(dto.getCategory())
			.authCond(dto.getAuthCond())
			.planetImgUrl(planetImgUrl)
			.standardVerificationImgUrl(stdVerificationImgUrl)
			.build());
	}

	/**
	 * 사용자 정의 행성 이미지를 업로드합니다.
	 *
	 * @param customPlanetImg 업로드할 사용자 정의 행성 이미지 파일입니다.
	 * @param memberId 회원 ID입니다.
	 * @return 업로드된 이미지의 URL을 반환합니다.
	 */
	private String uploadCustomPlanetImg(MultipartFile customPlanetImg, Long memberId) {
		FileMetaInfo fileMetaInfo = s3ImageService.uploadPlanetImg(customPlanetImg, memberId);
		return fileMetaInfo.getUrl();
	}

	/**
	 * 표준 검증 이미지를 업로드합니다.
	 *
	 * @param stdVerificationImg 업로드할 표준 검증 이미지 파일입니다.
	 * @param memberId 회원 ID입니다.
	 * @return 업로드된 이미지의 URL을 반환합니다.
	 */
	private String uploadStdVerificationImg(MultipartFile stdVerificationImg, Long memberId) {
		FileMetaInfo fileMetaInfo = s3ImageService.uploadStandardVerificationImg(stdVerificationImg, memberId);
		return fileMetaInfo.getUrl();
	}

}
