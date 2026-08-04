package com.bnote.domain.member.dto.response;

public record TokenResponse(
	String accessToken,
	String refreshToken,
	boolean isNewMember
) {
}