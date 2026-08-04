package com.bnote.domain.member.dto.response;

import com.bnote.domain.member.entity.Member;
import com.bnote.domain.member.entity.SocialType;

public record MemberResponse(
	Long id,
	String nickname,
	String profileImageUrl,
	SocialType socialType
) {
	public static MemberResponse from(Member member) {
		return new MemberResponse(
			member.getId(),
			member.getNickname(),
			member.getProfileImageUrl(),
			member.getSocialType()
		);
	}
}