package com.bnote.domain.member.service;

import com.bnote.domain.member.dto.response.MemberResponse;
import com.bnote.domain.member.entity.Member;
import com.bnote.domain.member.exception.MemberException;
import com.bnote.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;

	public MemberResponse getMe(Long memberId) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(MemberException::notFound);
		return MemberResponse.from(member);
	}

	@Transactional
	public void withdraw(Long memberId) {
		if (!memberRepository.existsById(memberId)) {
			throw MemberException.notFound();
		}
		memberRepository.deleteById(memberId);
	}
}