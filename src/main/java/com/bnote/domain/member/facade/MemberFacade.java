package com.bnote.domain.member.facade;

import com.bnote.domain.member.dto.response.MemberResponse;
import com.bnote.domain.member.service.MemberService;
import org.springframework.stereotype.Service;

@Service
public class MemberFacade {

	private final MemberService memberService;

	public MemberFacade(MemberService memberService) {
		this.memberService = memberService;
	}

	public MemberResponse getMe(Long memberId) {
		return memberService.getMe(memberId);
	}

	public void withdraw(Long memberId) {
		memberService.withdraw(memberId);
	}
}