package com.planetrush.planetrush.verification.facade;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.planetrush.planetrush.infra.s3.S3ImageService;
import com.planetrush.planetrush.infra.s3.dto.FileMetaInfo;
import com.planetrush.planetrush.verification.service.VerificationService;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Component
public class VerificationFacade {

	private final S3ImageService s3ImageService;
	private final VerificationService verificationService;

	/**
	 * 사진을 저장하고 유사도를 검사해 결과를 보여줍니다.
	 * @param verificationImg 인증 사진
	 * @param memberId 유저의 고유 id
	 * @param planetId 행성의 고유 id
	 */
	public void saveImgAndVerifyChallenge(MultipartFile verificationImg, Long memberId, Long planetId) {
		FileMetaInfo fileMetaInfo = s3ImageService.uploadVerificationImg(verificationImg, memberId);
		String userImgUrl = fileMetaInfo.getUrl();
		verificationService.verifyTodayChallenge(memberId, planetId, userImgUrl);
	}
}
