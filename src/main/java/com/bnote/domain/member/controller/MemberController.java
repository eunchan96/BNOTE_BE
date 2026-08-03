package com.bnote.domain.member.controller;

import com.bnote.domain.member.dto.response.MemberResponse;
import com.bnote.domain.member.service.MemberService;
import com.bnote.global.rq.Rq;
import com.bnote.global.response.RsData;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {

	private final MemberService memberService;
	private final Rq rq;

	public MemberController(MemberService memberService, Rq rq) {
		this.memberService = memberService;
		this.rq = rq;
	}

	@GetMapping("/me")
	public RsData<MemberResponse> getMe() {
		MemberResponse member = memberService.getMe(rq.getActorIdOrThrow());
		return RsData.ok("내 정보 조회 성공", member);
	}

	@DeleteMapping("/me")
	public RsData<Void> withdraw() {
		memberService.withdraw(rq.getActorIdOrThrow());
		return RsData.ok("회원 탈퇴가 완료되었습니다.");
	}
}