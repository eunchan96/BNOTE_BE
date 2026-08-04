package com.bnote.domain.mypage.profile.dto.response;

import com.bnote.domain.member.entity.Member;

public record ProfileResponse(
	String name,
	String church,
	String department,
	String position,
	String photoUrl
) {
	public static ProfileResponse from(Member member) {
		return new ProfileResponse(
			member.getName(), member.getChurch(), member.getDepartment(), member.getPosition(), member.getPhotoUrl()
		);
	}
}