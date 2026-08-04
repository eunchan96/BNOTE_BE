package com.bnote.global.auth.oauth;

import com.bnote.domain.member.entity.SocialType;
import com.bnote.domain.member.exception.MemberException;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class GoogleOAuthClient implements OAuthClient {

	private static final String TOKEN_URI = "https://oauth2.googleapis.com/token";
	private static final String USER_INFO_URI = "https://www.googleapis.com/oauth2/v3/userinfo";

	private final RestClient restClient = RestClient.create();

	@Value("${oauth.google.client-id}")
	private String clientId;

	@Value("${oauth.google.client-secret}")
	private String clientSecret;

	@Value("${oauth.google.redirect-uri}")
	private String redirectUri;

	@Override
	public SocialType supportType() {
		return SocialType.GOOGLE;
	}

	@Override
	public SocialUserInfo getUserInfo(String authCode) {
		String googleAccessToken = requestAccessToken(authCode);
		return requestUserInfo(googleAccessToken);
	}

	private String requestAccessToken(String authCode) {
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", clientId);
		body.add("client_secret", clientSecret);
		body.add("redirect_uri", redirectUri);
		body.add("code", authCode);

		GoogleTokenResponse response = restClient.post()
				.uri(TOKEN_URI)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.body(body)
				.retrieve()
				.body(GoogleTokenResponse.class);

		if (response == null || response.accessToken() == null) {
			throw MemberException.oauthFailed("구글 토큰 발급에 실패했습니다.");
		}
		return response.accessToken();
	}

	private SocialUserInfo requestUserInfo(String googleAccessToken) {
		Map<String, Object> response = restClient.get()
				.uri(USER_INFO_URI)
				.header("Authorization", "Bearer " + googleAccessToken)
				.retrieve()
				.body(Map.class);

		if (response == null) {
			throw MemberException.oauthFailed("구글 사용자 정보 조회에 실패했습니다.");
		}

		String socialId = String.valueOf(response.get("sub"));
		String nickname = (String) response.getOrDefault("name", "구글 사용자");
		String profileImageUrl = (String) response.get("picture");

		return new SocialUserInfo(socialId, nickname, profileImageUrl);
	}

	private record GoogleTokenResponse(
			@JsonProperty("token_type") String tokenType,
			@JsonProperty("access_token") String accessToken,
			@JsonProperty("id_token") String idToken
	) {
	}
}