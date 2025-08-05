package com.planetrush.planetrush.infra.s3;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.planetrush.planetrush.infra.s3.dto.FileMetaInfo;
import com.planetrush.planetrush.infra.s3.exception.S3Exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3ImageServiceImpl implements S3ImageService {

	private final AmazonS3 amazonS3;
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	private final String CUSTOM_PLANET_IMG_DIR = "custom_planet_img/";
	private final String STD_VERIFICATION_IMG_DIR = "std_verification_img/";
	private final String VERIFICATION_IMG_DIR = "verification_img/";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FileMetaInfo uploadPlanetImg(MultipartFile file, long memberId) {
		String url = upload(file, CUSTOM_PLANET_IMG_DIR, memberId);
		String name = file.getOriginalFilename();
		String format = getFileExtension(name);
		long size = file.getSize();
		return FileMetaInfo.builder()
			.url(url)
			.name(name)
			.format(format)
			.size(size)
			.build();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FileMetaInfo uploadStandardVerificationImg(MultipartFile file, long memberId) {
		String url = upload(file, STD_VERIFICATION_IMG_DIR, memberId);
		String name = file.getOriginalFilename();
		String format = getFileExtension(name);
		long size = file.getSize();
		return FileMetaInfo.builder()
			.url(url)
			.name(name)
			.format(format)
			.size(size)
			.build();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FileMetaInfo uploadVerificationImg(MultipartFile file, Long memberId) {
		String url = upload(file, VERIFICATION_IMG_DIR, memberId);
		String name = file.getOriginalFilename();
		String format = getFileExtension(name);
		long size = file.getSize();
		return FileMetaInfo.builder()
			.url(url)
			.name(name)
			.format(format)
			.size(size)
			.build();
	}

	/**
	 * S3로 파일 업로드 하기
	 * @param file 변환된 File
	 * @param dirName 디렉토리 명
	 * @param memberId 업로드하는 멤버 아이디
	 * @return 이미지 url
	 */
	private String upload(MultipartFile file, String dirName, Long memberId) {
		if (file.isEmpty()) {
			throw new S3Exception("Image file is empty");
		}
		String fileName = dirName + memberId + "/" + UUID.randomUUID() + file.getOriginalFilename();
		try (InputStream inputStream = file.getInputStream()) {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(file.getSize());
			amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, metadata)
				.withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new S3Exception("error: MultipartFile -> S3 upload fail");
		}
		return amazonS3.getUrl(bucket, fileName).toString();
	}

	/**
	 * S3로 업로드 요청 후 url 반환
	 * @param uploadFile 파일
	 * @param fileName 파일명
	 * @return 이미지 url
	 */
	private String putS3(File uploadFile, String fileName) {
		amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(
			CannedAccessControlList.PublicRead));
		return amazonS3.getUrl(bucket, fileName).toString();
	}

}
