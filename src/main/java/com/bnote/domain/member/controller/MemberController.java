package com.bnote.domain.member.controller;

import com.bnote.domain.member.dto.response.MemberResponse;
import com.bnote.domain.member.facade.MemberFacade;
import com.bnote.global.response.RsData;
import com.bnote.global.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController implements MemberControllerDocs {

	private final MemberFacade memberFacade;
	private final Rq rq;

	@GetMapping("/me")
	public RsData<MemberResponse> getMe() {
		MemberResponse member = memberFacade.getMe(rq.getActorIdOrThrow());
		return RsData.ok("내 정보 조회 성공", member);
	}

	@DeleteMapping("/me")
	public RsData<Void> withdraw() {
		memberFacade.withdraw(rq.getActorIdOrThrow());
		return RsData.ok("회원 탈퇴가 완료되었습니다.");
	}
}