package com.bnote.global.auth.oauth;

import com.bnote.domain.member.entity.SocialType;

public interface OAuthClient {

	SocialType supportType();

	/**
	 * 프론트에서 전달받은 인가 코드(authCode)로 소셜 제공자와 직접 통신해
	 * access token을 교환하고, 사용자 정보를 조회한다.
	 */
	SocialUserInfo getUserInfo(String authCode);
}