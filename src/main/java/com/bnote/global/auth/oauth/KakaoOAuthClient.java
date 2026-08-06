package com.bnote.global.auth.oauth;

import com.bnote.domain.member.entity.SocialType;
import com.bnote.domain.member.exception.MemberException;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.Map;

@Slf4j
@Component
public class KakaoOAuthClient implements OAuthClient {

	private static final String TOKEN_URI = "https://kauth.kakao.com/oauth/token";
	private static final String USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";

	private final RestClient restClient = RestClient.create();

	@Value("${oauth.kakao.client-id}")
	private String clientId;

	@Value("${oauth.kakao.client-secret:}")
	private String clientSecret;

	@Value("${oauth.kakao.redirect-uri}")
	private String redirectUri;

	@Override
	public SocialType supportType() {
		return SocialType.KAKAO;
	}

	@Override
	public SocialUserInfo getUserInfo(String authCode) {
		String kakaoAccessToken = requestAccessToken(authCode);
		return requestUserInfo(kakaoAccessToken);
	}

	private String requestAccessToken(String authCode) {
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", clientId);
		body.add("redirect_uri", redirectUri);
		body.add("code", authCode);
		if (clientSecret != null && !clientSecret.isBlank()) {
			body.add("client_secret", clientSecret);
		}

		KakaoTokenResponse response;
		try {
			response = restClient.post()
					.uri(TOKEN_URI)
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.body(body)
					.retrieve()
					.body(KakaoTokenResponse.class);
		} catch (RestClientResponseException e) {
			log.error("[KakaoOAuthClient] 토큰 발급 실패: status={}, body={}", e.getStatusCode(), e.getResponseBodyAsString());
			throw MemberException.oauthFailed("카카오 토큰 발급에 실패했습니다: " + e.getResponseBodyAsString());
		}

		if (response == null || response.accessToken() == null) {
			throw MemberException.oauthFailed("카카오 토큰 발급에 실패했습니다.");
		}
		return response.accessToken();
	}

	private SocialUserInfo requestUserInfo(String kakaoAccessToken) {
		Map<String, Object> response = restClient.get()
				.uri(USER_INFO_URI)
				.header("Authorization", "Bearer " + kakaoAccessToken)
				.retrieve()
				.body(Map.class);

		if (response == null) {
			throw MemberException.oauthFailed("카카오 사용자 정보 조회에 실패했습니다.");
		}

		String socialId = String.valueOf(response.get("id"));

		@SuppressWarnings("unchecked")
		Map<String, Object> kakaoAccount = (Map<String, Object>) response.getOrDefault("kakao_account", Map.of());
		@SuppressWarnings("unchecked")
		Map<String, Object> profile = (Map<String, Object>) kakaoAccount.getOrDefault("profile", Map.of());

		String nickname = (String) profile.getOrDefault("nickname", "카카오 사용자");
		String profileImageUrl = (String) profile.get("profile_image_url");

		return new SocialUserInfo(socialId, nickname, profileImageUrl);
	}

	private record KakaoTokenResponse(
			@JsonProperty("token_type") String tokenType,
			@JsonProperty("access_token") String accessToken,
			@JsonProperty("refresh_token") String refreshToken
	) {
	}
}