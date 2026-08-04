package com.bnote.domain.mypage.profile.dto.request;

public record ProfileRequest(
	String name,
	String church,
	String department,
	String position
) {
}