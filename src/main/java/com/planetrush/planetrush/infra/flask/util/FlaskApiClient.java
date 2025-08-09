package com.planetrush.planetrush.infra.flask.util;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.planetrush.planetrush.infra.flask.exception.FlaskConnectionFailedException;
import com.planetrush.planetrush.infra.flask.exception.ImageSimilarityCheckErrorException;
import com.planetrush.planetrush.infra.flask.exception.InvalidImageUrlCountException;
import com.planetrush.planetrush.infra.flask.exception.ProgressAvgNotFoundException;
import com.planetrush.planetrush.infra.flask.res.FlaskResponse;
import com.planetrush.planetrush.infra.flask.res.FlaskResponseDto;
import com.planetrush.planetrush.member.service.dto.GetMyProgressAvgDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FlaskApiClient {

	@Value("${flask.verifyurl}")
	private String verifyUrl;
	@Value("${flask.progressavgurl}")
	private String progressAvgUrl;
	private final RestTemplate restTemplate;

	@Retryable(
		retryFor = FlaskConnectionFailedException.class,
		noRetryFor = {InvalidImageUrlCountException.class, ImageSimilarityCheckErrorException.class},
		maxAttempts = 5,
		backoff = @Backoff(delay = 1000, multiplier = 2.0, maxDelay = 17000, random = true)
	)
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
				throw new ImageSimilarityCheckErrorException("An error occurred during the image similarity check process");
			}
			return res.getData();
		} catch (Exception e) {
			throw new FlaskConnectionFailedException();
		}
	}

	@Retryable(
		retryFor = FlaskConnectionFailedException.class,
		noRetryFor = {InvalidImageUrlCountException.class, ImageSimilarityCheckErrorException.class},
		maxAttempts = 5,
		backoff = @Backoff(delay = 1000, multiplier = 2.0, maxDelay = 17000, random = true)
	)
	public GetMyProgressAvgDto getMyProgressAvg(Long memberId) {
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
		} catch (Exception e) {
			throw new FlaskConnectionFailedException();
		}
	}

}
