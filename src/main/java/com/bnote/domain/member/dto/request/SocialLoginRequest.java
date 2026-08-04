package com.bnote.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * 카카오/구글 로그인 공통 요청 DTO. 프론트에서 소셜 SDK로 받은 인가 코드(authCode)를 전달한다.
 */
public record SocialLoginRequest(
	@NotBlank
	String authCode
) {
}