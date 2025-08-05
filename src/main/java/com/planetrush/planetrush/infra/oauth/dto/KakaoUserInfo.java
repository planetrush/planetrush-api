package com.planetrush.planetrush.infra.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class KakaoUserInfo {

	@JsonProperty("id")
	private Long id;
	@JsonProperty("connected_at")
	private String connectedAt;
	@JsonProperty("kakao_account")
	private KakaoAccount kakaoAccount;

	@Getter
	public static class KakaoAccount {
		@JsonProperty("has_email")
		private boolean hasEmail;

		@JsonProperty("email_needs_agreement")
		private boolean emailNeedsAgreement;

		@JsonProperty("is_email_valid")
		private boolean isEmailValid;

		@JsonProperty("is_email_verified")
		private boolean isEmailVerified;

		@JsonProperty("email")
		private String email;
	}

}
