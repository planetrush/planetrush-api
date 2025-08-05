package com.planetrush.planetrush.infra.oauth.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.planetrush.planetrush.infra.oauth.dto.KakaoUserInfo;

@Component
public class KakaoUtil {

	@Value("${kakao.loginurl}")
	private String loginUrl;

	@Value("${kakao.secret.key}")
	private String SERVICE_APP_ADMIN_KEY;

	@Value("${kakao.logouturl}")
	private String logoutUrl;

	/**
	 * 카카오에서 유저 정보를 불러옵니다.
	 * @param accessToken 카카오에서 발급해 준 accessToken
	 * @return 카카오에서 넘겨주는 유저 정보
	 * @see KakaoUserInfo
	 */
	public KakaoUserInfo getUserInfo(String accessToken) {
		KakaoUserInfo userInfo = new KakaoUserInfo();
		WebClient webClient = WebClient.builder().build();

		String response = webClient.get()
			.uri(loginUrl)
			.header("Authorization", ("Bearer " + accessToken))
			.header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
			.retrieve()
			.bodyToMono(String.class)
			.block();

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			userInfo = objectMapper.readValue(response, new TypeReference<KakaoUserInfo>() {
			});
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return userInfo;
	}

	/**
	 * 카카오에서 로그아웃을 진행합니다.
	 * @param id 카카오에서의 고유 id값
	 */
	public void kakaoLogout(String id) {
		WebClient webClient = WebClient.builder()
			.defaultHeader("Content-Type", "application/x-www-form-urlencoded")
			.build();

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("target_id_type", "user_id");
		params.add("target_id", id);
		String response = webClient.post()
			.uri(logoutUrl)
			.header("Authorization", ("KakaoAK " + SERVICE_APP_ADMIN_KEY))
			.body(BodyInserters.fromFormData(params))
			.retrieve()
			.bodyToMono(String.class)
			.block();
	}
}