package com.planetrush.planetrush.infra.flask.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.planetrush.planetrush.infra.flask.exception.FlaskServerNotConnectedException;
import com.planetrush.planetrush.infra.flask.exception.ImageSimilarityCheckErrorException;
import com.planetrush.planetrush.infra.flask.exception.InvalidImageUrlCountException;
import com.planetrush.planetrush.infra.flask.exception.ProgressAvgNotFoundException;
import com.planetrush.planetrush.infra.flask.res.FlaskResponse;
import com.planetrush.planetrush.member.service.dto.GetMyProgressAvgDto;
import com.planetrush.planetrush.verification.service.dto.FlaskResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class FlaskUtil {

	@Value("${flask.verifyurl}")
	private String verifyUrl;
	@Value("${flask.progressavgurl}")
	private String progressAvgUrl;
	private final RestTemplate restTemplate;

	public FlaskResponseDto verifyChallengeImg(String standardImgUrl, String targetImgUrl) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
			Map<String, String> body = new HashMap<>();
			body.put("standardImgUrl", standardImgUrl);
			body.put("targetImgUrl", targetImgUrl);
			RequestEntity<Object> requestEntity = new RequestEntity<>(
				body,
				headers,
				HttpMethod.POST,
				new URI(verifyUrl)
			);
			FlaskResponse<FlaskResponseDto> res = restTemplate.exchange(
				requestEntity,
				new ParameterizedTypeReference<FlaskResponse<FlaskResponseDto>>() {
				}
			).getBody();
			String code = res.getCode();
			if (code.equals("8000")) {
				throw new InvalidImageUrlCountException("The number of image URLs must be exactly two");
			} else if (code.equals("8001")) {
				throw new ImageSimilarityCheckErrorException(
					"An error occurred during the image similarity check process");
			}
			return res.getData();
		} catch (URISyntaxException | RestClientException e) {
			throw new FlaskServerNotConnectedException("Flask Server Not Connected");
		}
	}

	public GetMyProgressAvgDto getMyProgressAvg(Long memberId) {
		log.info("Flask API Call");
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
			RequestEntity<Object> requestEntity = new RequestEntity<>(
				headers,
				HttpMethod.GET,
				new URI(progressAvgUrl + "/" + memberId)
			);
			FlaskResponse<GetMyProgressAvgDto> res = restTemplate.exchange(
				requestEntity,
				new ParameterizedTypeReference<FlaskResponse<GetMyProgressAvgDto>>() {
				}
			).getBody();
			if (res.getCode().equals("8002")) {
				throw new ProgressAvgNotFoundException("Progress Average not found");
			}
			return res.getData();
		} catch (URISyntaxException | RestClientException e) {
			log.info("예외={}", e);
			throw new FlaskServerNotConnectedException("Flask server not connected");
		}
	}

}
