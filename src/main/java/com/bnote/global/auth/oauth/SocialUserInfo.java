package com.bnote.global.auth.oauth;

public record SocialUserInfo(
	String socialId,
	String nickname,
	String profileImageUrl
) {
}