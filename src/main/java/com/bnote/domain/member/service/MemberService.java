package com.bnote.domain.member.service;

import com.bnote.domain.member.dto.response.MemberResponse;
import com.bnote.domain.member.entity.Member;
import com.bnote.domain.member.repository.MemberRepository;
import com.bnote.global.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public MemberResponse getMe(Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new ServiceException("404-1", "회원을 찾을 수 없습니다."));
		return MemberResponse.from(member);
	}

	@Transactional
	public void withdraw(Long memberId) {
		if (!memberRepository.existsById(memberId)) {
			throw new ServiceException("404-1", "회원을 찾을 수 없습니다.");
		}
		memberRepository.deleteById(memberId);
	}
}